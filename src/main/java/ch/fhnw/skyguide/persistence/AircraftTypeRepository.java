package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.ActivityType;
import ch.fhnw.skyguide.domain.AircraftType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AircraftTypeRepository extends CrudRepository<ActivityType, Integer> {
    @Query(value = "SELECT * FROM aircraft_type WHERE name = ?1", nativeQuery = true)
    AircraftType findByName(@Param("name") String name);
}