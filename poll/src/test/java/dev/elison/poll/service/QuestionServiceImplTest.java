package dev.elison.poll.service;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class QuestionServiceImplTest {

    @MockBean
    private QuestionRepository questionRepository;
    private QuestionService questionService;
    private Instant now;

    @Before
    public void setup() {
        if (questionService == null) {
            questionService = new QuestionServiceImpl(questionRepository);
            now = Instant.now();
        }

        Question question = new Question();
        question.setId(1L);
        question.setPubDate(now.minus(3, ChronoUnit.MINUTES));
        Choice choice = new Choice();
        choice.setId(1L);
        question.setChoices(Collections.singleton(choice));

        Mockito.when(questionRepository.findByPubDateLessThanEqual(now)).thenReturn(Collections.singleton(question));
        Mockito.when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
    }

    @Test
    public void getRecentQuestions() {
        assertThat(StreamSupport.stream(questionService.getRecentQuestions(now).spliterator(), false).count()).isEqualTo(1);
    }

    @Test
    public void getQuestionById() {
        assertThat(questionService.getQuestionById(1L).getId()).isEqualTo(1L);
    }

    @Test
    public void vote() {
        Vote vote = new Vote();
        vote.setChoiceId(1L);
        assertThat(questionService.getQuestionById(1L).getChoice(1L).get().getVotes()).isEqualTo(0);
        questionService.vote(1L, vote);
        assertThat(questionService.getQuestionById(1L).getChoice(1L).get().getVotes()).isEqualTo(1);
    }
}