package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.ActivityType;
import ch.fhnw.skyguide.domain.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {
    @Query(value = "SELECT * FROM activity_type WHERE name = ?1", nativeQuery = true)
    ActivityType findByName(@Param("name") String name);
}