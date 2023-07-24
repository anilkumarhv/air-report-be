package com.anil.airreportbe.model.entity;

public enum AircraftConditionType {
    IC("ICING_CONDITION"),
    TB("TURBULENCE_CONDITION"),
    SC("SKY_CONDITION"),
    QC("QUALITY_CONDITION");


    private final String aircraftCondition;

    AircraftConditionType(String aircraftCondition) {
        this.aircraftCondition = aircraftCondition;
    }

    public String getAircraftCondition() {
        return aircraftCondition;
    }
}
