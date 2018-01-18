package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.HeightType;
import org.springframework.data.repository.CrudRepository;

public interface HeightTypeRepository extends CrudRepository<HeightType, Integer> {
    HeightType findByName(String name);
}