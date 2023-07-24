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
@Table(name = "sky_condition")
public class SkyCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    private Long id;

    private String skyCover;
    private Integer cloudBaseFtAgl;
    private Integer cloudTopFtMsl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    @JsonBackReference
    private Pirep pirep;
}
