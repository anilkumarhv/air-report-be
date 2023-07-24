package com.anil.airreportbe.model.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class AircraftCondition {
    Boolean isIcingType;
    Boolean isSkyType;
    Boolean isTurbulenceType;
    Boolean isQualityControlFlagType;
}
