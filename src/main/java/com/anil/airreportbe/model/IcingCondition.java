package com.anil.airreportbe.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "icing_condition")
public class IcingCondition {
    @XmlAttribute(name = "icing_type")
    private String icing_type;
    @XmlAttribute(name = "icing_intensity")
    private String icing_intensity;
    @XmlAttribute(name = "icing_base_ft_msl")
    private int icing_base_ft_msl;
    @XmlAttribute(name = "icing_top_ft_msl")
    private int icing_top_ft_msl;
}
