package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Field;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository<Field, Integer> {
    Field findByName(String name);
}