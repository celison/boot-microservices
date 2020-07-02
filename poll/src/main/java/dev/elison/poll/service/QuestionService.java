package dev.elison.poll.service;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;

import java.time.Instant;

public interface QuestionService {
    Iterable<Question> getRecentQuestions(Instant before);

    Question getQuestionById(Long id);
    void addChoice(Long id, Choice choice);

    void vote(Long id, Vote vote);
}
