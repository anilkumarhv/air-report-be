package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.TurbulenceCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurbulenceConditionRepository extends JpaRepository<TurbulenceCondition,Long> {
}
