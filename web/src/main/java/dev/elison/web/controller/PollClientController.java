package dev.elison.web.controller;

import dev.elison.poll.common.Question;
import dev.elison.poll.common.Vote;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    @PostMapping("/{id}/vote")
    public void vote(@PathVariable("id") Long id, @RequestBody Vote vote) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (vote.getChoiceId() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid choiceID");
        }
        restTemplate.exchange(QUESTION_BASE_URL + id + "/vote", HttpMethod.POST, new HttpEntity<>(vote, httpHeaders), Void.class);
    }
}
