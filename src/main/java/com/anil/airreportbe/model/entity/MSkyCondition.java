package com.anil.airreportbe.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sky_condition")
public class MSkyCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String skyCover;
    private Integer cloudBaseFtAgl;
    private Integer cloudTopFtMsl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metar_id")
//    @JsonBackReference
    @JsonIgnore
    private Metar metar;
}