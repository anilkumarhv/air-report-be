package com.anil.airreportbe.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Station")
@XmlType
public class Station {
    private String station_id;
    private String wmo_id;
    private double latitude;
    private double longitude;
    private double elevation_m;
    private String site;
    private String state;
    private String country;
    private String site_type;
}
