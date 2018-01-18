package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.ActivityType;
import org.springframework.data.repository.CrudRepository;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {
    ActivityType findByName(String name);
}