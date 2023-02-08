package com.anil.airreportbe.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "quality_control_flags")
public class QualityControlFlags {
    private String mid_point_assumed;
    private String no_time_stamp;
    private String flt_lvl_range;
    private String above_ground_level_indicated;
    private String no_flt_lvl;
    private String bad_location;
}
