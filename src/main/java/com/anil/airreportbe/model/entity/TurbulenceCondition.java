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
@Table(name = "turbulence_condition")
public class TurbulenceCondition implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    private Long id;

    private String turbulenceType;
    private String turbulenceIntensity;
    private Integer turbulenceBaseFtMsl;
    private Integer turbulenceTopFtMsl;
    private String turbulenceFreq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    @JsonBackReference
    private Pirep pirep;
}
