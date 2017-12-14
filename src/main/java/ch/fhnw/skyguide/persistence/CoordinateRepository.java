package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Coordinate;
import org.springframework.data.repository.CrudRepository;

public interface CoordinateRepository extends CrudRepository<Coordinate, Integer> {
}
