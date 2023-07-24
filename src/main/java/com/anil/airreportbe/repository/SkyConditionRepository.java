package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.SkyCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkyConditionRepository extends JpaRepository<SkyCondition,Long> {
}
