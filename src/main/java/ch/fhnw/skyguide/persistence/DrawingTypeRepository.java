package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.DrawingType;
import org.springframework.data.repository.CrudRepository;

public interface DrawingTypeRepository extends CrudRepository<DrawingType, Integer> {
    DrawingType findByName(String name);
}