package dev.elison.poll.common;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String choiceText;
    private Long votes = 0L;
    @ManyToOne
    @JsonBackReference
    private Question question;

    public Choice() {
    }

    public Choice(String choiceText) {
        this.choiceText = choiceText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Choice vote() {
        votes++;
        return this;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return id.equals(choice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
