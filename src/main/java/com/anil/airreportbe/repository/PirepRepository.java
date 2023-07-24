package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.Pirep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface PirepRepository extends JpaRepository<Pirep, Long>, JpaSpecificationExecutor<Pirep> {

    List<Pirep> findAllByAircraftCodeAndObservationTimeBetween(String code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);

    @Query(value = "select * from pirep where code = ?1 and observation_time between ?2 and ?3 order by observation_time desc", nativeQuery = true)
    List<Pirep> findAllByAircraftCodeAndObservationTimeBetweenOrderByObservationTimeDesc(String code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);

    List<Pirep> findAllByAircraftCodeInAndObservationTimeBetween(List<String> code, ZonedDateTime observationStartTime, ZonedDateTime observationEndTime);


}
