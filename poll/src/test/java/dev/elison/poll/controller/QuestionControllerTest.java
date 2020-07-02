package dev.elison.poll.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.service.QuestionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;
    private Instant now;

    private Question question;
    private Choice choice;

    @Before
    public void setup() {
        if (now == null) {
            now = Instant.now();
        }

        question = new Question();
        question.setId(1L);
        question.setQuestionText("Test");
        question.setPubDate(now.minus(3, ChronoUnit.MINUTES));
        choice = new Choice();
        choice.setChoiceText("Test Choice 1");
        choice.setId(1L);
        question.setChoices(Collections.singleton(choice));

        Mockito.when(questionService.getRecentQuestions(now)).thenReturn(Collections.singletonList(question));
        Mockito.when(questionService.getQuestionById(1L)).thenReturn(question);
    }

    @Test
    public void getQuestions() throws Exception {
        mockMvc.perform(get("/question/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getQuestion() throws Exception {
        mockMvc.perform(get("/question/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.questionText").value("Test"));

        verify(questionService, times(1)).getQuestionById(1L);
        verifyNoMoreInteractions(questionService);
    }

    @Test
    public void vote() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        Vote vote = new Vote(1L);
        String requestJson = ow.writeValueAsString(vote);

        mockMvc.perform(post("/question/{id}/vote", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson))
                .andExpect(status().isOk());

        verify(questionService, times(1)).vote(1L, vote);
        verifyNoMoreInteractions(questionService);
    }

    @Test
    public void addChoice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        Choice choice = new Choice("Mock Question?");
        choice.setId(1L);
        String requestJson = ow.writeValueAsString(choice);

        mockMvc.perform(post("/question/{id}/choice", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        verify(questionService, times(1)).addChoice(1L, choice);
        verifyNoMoreInteractions(questionService);

    }
}