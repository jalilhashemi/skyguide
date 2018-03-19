package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.FormType;
import org.springframework.data.repository.CrudRepository;

public interface FormTypeRepository extends CrudRepository<FormType, Integer> {
    //FormType findByName(String name);
    FormType findByActivityType_NameAndAircraftType_Name(String activityType, String aircraftType);
}