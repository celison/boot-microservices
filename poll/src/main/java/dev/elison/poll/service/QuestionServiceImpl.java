package dev.elison.poll.service;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.repository.ChoiceRepository;
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
    private final ChoiceRepository choiceRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Iterable<Question> getRecentQuestions(Instant before) {
        return questionRepository.findByPubDateLessThanEqual(before);
    }

    @Override
    public void saveQuestion(Question question) {
        if (question.getPubDate() == null) {
            question.setPubDate(Instant.now());
        }
        questionRepository.save(question);
        question.getChoices().forEach(choiceRepository::save);
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
    public void addChoice(Long id, Choice choice) {
        if (choice.getQuestion() != null) {
            throw new ResponseStatusException(BAD_REQUEST, "Choice already has Question");
        }

        Question question = getOr404(questionRepository, id);
        question.addChoice(choice);
        choiceRepository.save(choice);
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
