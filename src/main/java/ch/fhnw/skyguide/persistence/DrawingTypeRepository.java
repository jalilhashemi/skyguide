package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.DrawingType;
import org.springframework.data.repository.CrudRepository;

public interface DrawingTypeRepository extends CrudRepository<DrawingType, Integer> {
   // @Query(value = "SELECT * FROM activity_type WHERE name = ?1", nativeQuery = true)
    DrawingType findByName(/* @Param("name") */String name);
}