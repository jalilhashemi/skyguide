package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Application;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, String> {

}