package dev.elison.poll.common;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String questionText;
    private Instant pubDate;
    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    private Set<Choice> choices;

    public Question() {
    }

    public Question(String questionText, Instant pubDate, Set<Choice> choices) {
        this.questionText = questionText;
        this.pubDate = pubDate;
        this.choices = choices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Instant getPubDate() {
        return pubDate;
    }

    public void setPubDate(Instant pubDate) {
        this.pubDate = pubDate;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = new HashSet<>(choices);
    }

    public Optional<Choice> getChoice(Long id) {
        return getChoices().stream().filter(choice -> choice.getId().equals(id)).findAny();
    }

    public void addChoice(Choice choice) {
        choice.setQuestion(this);
        choices.add(choice);
    }
}
