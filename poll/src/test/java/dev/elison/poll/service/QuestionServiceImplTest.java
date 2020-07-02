package dev.elison.poll.service;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.repository.ChoiceRepository;
import dev.elison.poll.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class QuestionServiceImplTest {

    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private ChoiceRepository choiceRepository;
    private QuestionService questionService;
    private Instant now;

    @Before
    public void setup() {
        if (questionService == null) {
            questionService = new QuestionServiceImpl(questionRepository, choiceRepository);
            now = Instant.now();
        }

        Question question = new Question();
        question.setId(1L);
        question.setPubDate(now.minus(3, ChronoUnit.MINUTES));
        Choice choice = new Choice();
        choice.setId(1L);
        question.setChoices(Collections.singleton(choice));

        Question futureQuestion = new Question();
        futureQuestion.setId(2L);
        futureQuestion.setPubDate(now.plus(3, ChronoUnit.DAYS));
        choice = new Choice();
        choice.setId(2L);

        Mockito.when(questionRepository.findAll()).thenReturn(List.of(question, futureQuestion));
        Mockito.when(questionRepository.findByPubDateLessThanEqual(now)).thenReturn(Collections.singleton(question));
        Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        Mockito.when(questionRepository.findById(2L)).thenReturn(Optional.of(futureQuestion));

    }

    @Test
    public void getRecentQuestions() {
        assertThat(StreamSupport.stream(questionService.getRecentQuestions(now).spliterator(), false).count()).isEqualTo(1);
    }

    @Test
    public void getQuestionById() {
        assertThat(questionService.getQuestionById(1L).getId()).isEqualTo(1L);
    }

    @Test(expected = ResponseStatusException.class)
    public void getQuestionById_futureId() {
        questionService.getQuestionById(2L);
    }

    @Test(expected = ResponseStatusException.class)
    public void getQuestionById_wrongId() {
        questionService.getQuestionById(123L);
    }

    @Test
    public void vote() {
        Vote vote = new Vote(1L);
        assertThat(questionService.getQuestionById(1L).getChoice(1L).orElseThrow().getVotes()).isEqualTo(0);
        questionService.vote(1L, vote);
        assertThat(questionService.getQuestionById(1L).getChoice(1L).orElseThrow().getVotes()).isEqualTo(1);
    }


    @Test(expected = ResponseStatusException.class)
    public void vote_futureId() {
        Vote vote = new Vote(2L);
        questionService.vote(2L, vote);
    }

    @Test(expected = ResponseStatusException.class)
    public void vote_wrongId() {
        Vote vote = new Vote(2L);
        questionService.vote(3456L, vote);
    }

    @Test
    public void addChoice() {
        Choice newChoice = new Choice("Test Choice");
        newChoice.setId(2L);
        questionService.addChoice(1L, newChoice);
        assertThat(questionService.getQuestionById(1L).getChoice(2L).orElseThrow()).isNotNull();
        assertThat(questionService.getQuestionById(1L).getChoice(2L).orElseThrow().getQuestion()).isNotNull();
        assertThat(questionService.getQuestionById(1L).getChoice(2L).orElseThrow().getQuestion().getId()).isEqualTo(1L);
    }

    @Test(expected = ResponseStatusException.class)
    public void addChoice_failExistingQuestion() {
        Choice newChoice = new Choice("Test Choice");
        newChoice.setId(2L);
        newChoice.setQuestion(questionService.getQuestionById(1L));

        questionService.addChoice(1L, newChoice);
    }
}