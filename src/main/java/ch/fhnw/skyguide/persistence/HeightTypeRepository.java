package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.ActivityType;
import ch.fhnw.skyguide.domain.HeightType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HeightTypeRepository extends CrudRepository<HeightType, Integer> {
    //@Query(value = "SELECT * FROM height_type WHERE name = ?1", nativeQuery = true)
    HeightType findByName(String name);
}