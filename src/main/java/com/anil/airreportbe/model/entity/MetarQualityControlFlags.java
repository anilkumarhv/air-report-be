package com.anil.airreportbe.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "metar_quality_control_flags")
public class MetarQualityControlFlags implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String corrected;
    private String auto;
    private String autoStation;
    private String maintenanceIndicatorOn;
    private String noSignal;
    private String lightningSensorOff;
    private String freezingRainSensorOff;
    private String presentWeatherSensorOff;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metar_id")
    @JsonBackReference
    private Metar metar;
}
