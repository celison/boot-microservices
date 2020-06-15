package dev.elison.poll.controller;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static dev.elison.poll.util.ResponseUtil.get404Exception;
import static dev.elison.poll.util.ResponseUtil.getOr404;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public Iterable<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable("id") Long id) {
        return getOr404(questionRepository, id);
    }

    @PostMapping("/{id}/vote")
    public void vote(@PathVariable("id") Long id, @RequestBody Vote vote) {
        Question question = getOr404(questionRepository, id);
        Choice choice = question.getChoice(vote.getChoiceId()).orElseThrow(get404Exception());
        choice.vote();
        questionRepository.save(question);
    }
}
