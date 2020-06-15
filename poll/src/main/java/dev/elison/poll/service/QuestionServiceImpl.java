package dev.elison.poll.service;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static dev.elison.poll.util.ResponseUtil.get404Exception;
import static dev.elison.poll.util.ResponseUtil.getOr404;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Iterable<Question> getRecentQuestions() {
        return getRecentQuestions(Instant.now());
    }

    @Override
    public Iterable<Question> getRecentQuestions(Instant before) {
        return questionRepository.findByPubDateLessThanEqual(before);
    }

    @Override
    public Question getQuestionById(Long id) {
        Question question = getOr404(questionRepository, id);
        if (Instant.now().isBefore(question.getPubDate())) {
            throw getBeforePubDateException();
        }
        return question;
    }

    @Override
    public void vote(Long id, Vote vote) {
        Question question = getOr404(questionRepository, id);
        if (Instant.now().isBefore(question.getPubDate())) {
            throw getBeforePubDateException();
        }
        Choice choice = question.getChoice(vote.getChoiceId()).orElseThrow(get404Exception());
        choice.vote();
        questionRepository.save(question);
    }

    private ResponseStatusException getBeforePubDateException() {
        return new ResponseStatusException(BAD_REQUEST, "Question Not Published");
    }
}
