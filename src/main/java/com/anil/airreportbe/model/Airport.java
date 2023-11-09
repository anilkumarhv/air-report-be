package com.anil.airreportbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Airport implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("elevation_ft")
    private int elevationFt;
    @JsonProperty("elevation_m")
    private int elevationM;
    @JsonProperty("gps")
    private String gps;
    @JsonProperty("iata")
    private String iata;
    @JsonProperty("icao")
    private String icao;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("local")
    private String local;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("name")
    private String name;
    @JsonProperty("reporting")
    private boolean reporting;
    @JsonProperty("runways")
    private Runway[] runways;
    @JsonProperty("state")
    private String state;
    @JsonProperty("type")
    private String type;
    @JsonProperty("website")
    private String website;
    @JsonProperty("wiki")
    private String wiki;

}
