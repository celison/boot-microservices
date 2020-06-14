package dev.elison.poll.repository;

import dev.elison.poll.common.Choice;
import org.springframework.data.repository.CrudRepository;

public interface ChoiceRepository extends CrudRepository<Choice, Long> {
}
