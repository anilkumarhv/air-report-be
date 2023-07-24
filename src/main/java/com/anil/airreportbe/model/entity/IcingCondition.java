package com.anil.airreportbe.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "icing_condition")
public class IcingCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    private Long id;

    private String icingType;
    private String icingIntensity;
    private Integer icingBaseFtMsl;
    private Integer icingTopFtMsl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    @JsonBackReference
    private Pirep pirep;
}
