package dev.elison.poll.controller;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import dev.elison.poll.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public Iterable<Question> getQuestions() {
        return questionService.getRecentQuestions(Instant.now());
    }

    @PostMapping("/")
    public void postQuestion(@RequestBody Question question) {
        questionService.saveQuestion(question);
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable("id") Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/{id}/choice")
    public void addChoice(@PathVariable("id") Long id, @RequestBody Choice choice) {
        questionService.addChoice(id, choice);
    }

    @PostMapping("/{id}/vote")
    public void vote(@PathVariable("id") Long id, @RequestBody Vote vote) {
        questionService.vote(id, vote);
    }
}
