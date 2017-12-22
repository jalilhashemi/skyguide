package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Time;
import org.springframework.data.repository.CrudRepository;

public interface TimeRepository extends CrudRepository<Time, Integer> {
}
