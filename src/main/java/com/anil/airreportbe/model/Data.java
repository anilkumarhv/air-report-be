package com.anil.airreportbe.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@lombok.Data
@XmlRootElement(name = "data")
public class Data {
    @XmlElement(name = "Station")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Station> Station;
    @XmlAttribute(name = "num_results")
    private int num_results;
}
