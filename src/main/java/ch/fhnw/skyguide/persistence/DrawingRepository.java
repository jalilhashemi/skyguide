package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Drawing;
import org.springframework.data.repository.CrudRepository;

public interface DrawingRepository extends CrudRepository<Drawing, Integer> {
}
