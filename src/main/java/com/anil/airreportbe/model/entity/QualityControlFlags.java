package com.anil.airreportbe.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quality_control_flags")
public class QualityControlFlags implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    private Long id;

    private String midPointAssumed;
    private String noTimeStamp;
    private String fltLvlRange;
    private String aboveGroundLevelIndicated;
    private String noFltLvl;
    private String badLocation;

    @ManyToOne /*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "pirep_id")
    @JsonBackReference
    private Pirep pirep;
}
