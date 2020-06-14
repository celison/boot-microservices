package dev.elison.web.controller;

import dev.elison.poll.common.Question;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/v1/poll")
public class PollClientController {
    private static final String QUESTION_BASE_URL = "http://poll-service/question/";
    private static final String CHOICE_BASE_URL = "http://poll-service/choice/";

    private final RestTemplate restTemplate;

    public PollClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public List<Question> getQuestions() {
        ResponseEntity<List<Question>> questions = restTemplate.exchange(QUESTION_BASE_URL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return questions.getBody();
    }
}
