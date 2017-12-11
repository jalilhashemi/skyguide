package ch.fhnw.skyguide.persistence;

import ch.fhnw.skyguide.domain.Application;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {

    @Query("SELECT a FROM Application a WHERE LOWER(a.adminKey) = LOWER(:adminKey)")
    Application findByAdminKey(@Param("adminKey") String adminKey);

    @Query("SELECT a FROM Application a WHERE LOWER(a.viewKey) = LOWER(:viewKey)")
    Application findByKey(@Param("viewKey") String viewKey);

}