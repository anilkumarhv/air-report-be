package com.anil.airreportbe.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pirep", indexes = {
        @Index(name = "idx_pirep_code_type", columnList = "code, type")
})
public class Pirep implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue
    private Long id;
    @Embedded
    private Aircraft aircraft;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private ZonedDateTime receiptTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private ZonedDateTime observationTime;
    private String aircraftRef;
    private Double latitude;
    private Double longitude;
    private Integer altitudeFtMsl;
    private Double tempC;
    private Integer windDirDegrees;
    private Integer windSpeedKt;
    private String reportType;
    private String rawText;
    private String visibilityStatuteMi;
    private String wxString;
    private String vertGustKt;

    @Embedded
    private AircraftCondition aircraftCondition;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    @Transient
    private List<QualityControlFlags> qualityControlFlags;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    @Transient
    private List<IcingCondition> icingConditions;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    @Transient
    private List<SkyCondition> skyConditions;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
    @Transient
    private List<TurbulenceCondition> turbulenceConditions;
}
