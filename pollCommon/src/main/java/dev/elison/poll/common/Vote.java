package dev.elison.poll.common;

import java.util.Objects;

public class Vote {
    private Long choiceId;

    public Vote() {
    }

    public Vote(Long choiceId) {
        this.choiceId = choiceId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(choiceId, vote.choiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId);
    }
}
