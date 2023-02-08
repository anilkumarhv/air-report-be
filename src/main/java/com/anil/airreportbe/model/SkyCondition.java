package com.anil.airreportbe.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name="sky_condition")
public class SkyCondition {
    @XmlAttribute(name = "sky_cover")
    private String sky_cover;
    @XmlAttribute(name = "cloud_base_ft_agl")
    private Integer cloud_base_ft_agl;
    @XmlAttribute(name = "cloud_top_ft_msl")
    private Integer cloud_top_ft_msl;
}
