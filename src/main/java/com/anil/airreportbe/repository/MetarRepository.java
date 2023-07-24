package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.Metar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MetarRepository extends JpaRepository<Metar, Long>, JpaSpecificationExecutor<Metar> {

    List<Metar> findAllByAircraftCodeAndObservationTimeBetween(String code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);

    @Query(value = "select * from metar where code = ?1 and observation_time between ?2 and ?3 order by observation_time desc", nativeQuery = true)
    List<Metar> findAllByAircraftCodeAndObservationTimeBetweenOrderByObservationTimeDesc(String code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);

    List<Metar> findAllByAircraftCodeInAndAircraftTypeAndObservationTimeBetween(List<String> code, String type, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);

//    List<Metar> findAllByAircraftCodeInAndAircraftType(String code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime)
}
