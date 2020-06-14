package dev.elison.poll.common;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.Instant;
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
        this.choices = choices;
    }
}
