package com.anil.airreportbe.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "AircraftReport")
public class AircraftReport {
    private String receipt_time;
    private String observation_time;
    private String aircraft_ref;
    private Double latitude;
    private Double longitude;
    private Integer altitude_ft_msl;
    private Double temp_c;
    private Integer wind_dir_degrees;
    private Integer wind_speed_kt;
    private String report_type;
    private String raw_text;

    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "quality_control_flags")
    private List<QualityControlFlags> quality_control_flags;
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "sky_condition")
    private List<SkyCondition> sky_condition;
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "turbulence_condition")
    private List<TurbulenceCondition> turbulence_condition;
    @JacksonXmlElementWrapper(useWrapping = false)
    @XmlElement(name = "icing_condition")
    private List<IcingCondition> icing_condition;
}