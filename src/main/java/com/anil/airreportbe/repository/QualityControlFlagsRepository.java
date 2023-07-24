package com.anil.airreportbe.repository;

import com.anil.airreportbe.model.entity.QualityControlFlags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualityControlFlagsRepository extends JpaRepository<QualityControlFlags,Long> {
}
