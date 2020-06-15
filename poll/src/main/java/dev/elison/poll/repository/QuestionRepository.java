package dev.elison.poll.repository;

import dev.elison.poll.common.Question;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findByPubDateLessThanEqual(Instant date);
}
