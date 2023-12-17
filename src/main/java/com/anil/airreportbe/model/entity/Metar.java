package com.anil.airreportbe.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metar", indexes = {
        @Index(name = "idx_metar_code_type", columnList = "code, type")
})
public class Metar implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Embedded
    private Aircraft aircraft;

    @Column(name = "raw_text", length = 5120)
    private String rawText;
    private String stationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "UTC")
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
    private ZonedDateTime observationTime;
    private Double latitude;
    private Double longitude;
    private Double tempC;
    private Double dewpointC;
    private String windDirDegrees;
    private Integer windSpeedKt;
    private Integer windGustKt;
    private String visibilityStatuteMi;
    private Double altimInHg;
    private Double seaLevelPressureMb;
    private String wxString;
    private String flightCategory;
    private Double threeHrPressureTendencyMb;
    private Double maxTC;
    private Double minTC;
    private Double maxT24hrC;
    private Double minT24hrC;
    private Double precipIn;
    private Double pcp3hrIn;
    private Double pcp6hrIn;
    private Double pcp24hrIn;
    private Double snowIn;
    private Integer vertVisFt;
    private String metarType;
    private Double elevationM;

    @Embedded
    private AircraftCondition aircraftCondition;

    @Transient
    @JsonIgnore
    private List<MetarQualityControlFlags> qualityControlFlags = new ArrayList<>();

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "metar", cascade = CascadeType.ALL)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metar")
//    @JsonManagedReference
//    @Transient
    @JsonIgnore
    private List<MSkyCondition> skyConditions;
}
