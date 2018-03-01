package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.AircraftType;
import org.springframework.data.repository.CrudRepository;

public interface AircraftTypeRepository extends CrudRepository<AircraftType, Integer> {
    AircraftType findByName(String name);
}