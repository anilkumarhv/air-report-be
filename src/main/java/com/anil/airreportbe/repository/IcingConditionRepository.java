package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.IcingCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IcingConditionRepository extends JpaRepository<IcingCondition, Long> {
}
