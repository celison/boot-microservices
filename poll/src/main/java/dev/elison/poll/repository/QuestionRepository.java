package dev.elison.poll.repository;

import dev.elison.poll.common.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
