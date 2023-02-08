package com.anil.airreportbe.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name ="turbulence_condition")
public class TurbulenceCondition {
    @XmlAttribute(name = "turbulence_type")
    private String turbulence_type;
    @XmlAttribute(name = "turbulence_intensity")
    private String turbulence_intensity;
    @XmlAttribute(name = "turbulence_base_ft_msl")
    private int turbulence_base_ft_msl;
    @XmlAttribute(name = "turbulence_top_ft_msl")
    private int turbulence_top_ft_msl;
    @XmlAttribute(name = "turbulence_freq")
    private String turbulence_freq;
}
