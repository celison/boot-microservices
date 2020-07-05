package dev.elison.poll.repository;

import dev.elison.poll.common.Choice;
import dev.elison.poll.common.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuestionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionRepositoryTest() {
    }

    @Test
    public void whenFindPubDateLessThanEqual_thenReturnPublished() {
        // given
        Question past = new Question("Test", Instant.now().plus(-1, ChronoUnit.DAYS), Set.of(new Choice("A")));
        entityManager.persist(past);
        Question future = new Question("Test2", Instant.now().plus(1, ChronoUnit.DAYS), Set.of(new Choice("B")));
        entityManager.persist(future);
        entityManager.flush();

        // when
        Iterable<Question> found = questionRepository.findByPubDateLessThanEqual(Instant.now());

        long size;
        if (found instanceof Collection) {
            size = ((Collection<Question>) found).size();
        } else {
            size = 0;
            for (Question question : found) {
                size++;
            }
        }

        // then
        assertThat(size).isEqualTo(1);
    }
}