package dev.elison.poll.service;

import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;

import java.time.Instant;

public interface QuestionService {
    Iterable<Question> getRecentQuestions();
    Iterable<Question> getRecentQuestions(Instant before);

    Question getQuestionById(Long id);

    void vote(Long id, Vote vote);
}
