package com.anil.airreportbe.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "response")
public class PiRepResponse {
    @XmlElement(name = "AircraftReport")
    private List<AircraftReport> data;
}


