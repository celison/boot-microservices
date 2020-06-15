package dev.elison.poll.util;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResponseUtil {
    public static <T, U> T getOr404(CrudRepository<T, U> repository, U id) {
        return repository.findById(id).orElseThrow(get404Exception());
    }

    public static Supplier<RuntimeException> get404Exception() {
        return () -> new ResponseStatusException(NOT_FOUND, "Unable to find resource");
    }
}
