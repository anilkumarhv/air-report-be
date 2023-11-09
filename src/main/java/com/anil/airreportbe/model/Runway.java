package com.anil.airreportbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Runway {
    @JsonProperty("bearing1")
    private String bearing1;
    @JsonProperty("bearing2")
    private String bearing2;
    @JsonProperty("ident1")
    private String ident1;
    @JsonProperty("ident2")
    private String ident2;
    @JsonProperty("length_ft")
    private int lengthFt;
    @JsonProperty("lights")
    private boolean lights;
    @JsonProperty("surface")
    private String surface;
    @JsonProperty("width_ft")
    private int widthFt;
}
