package com.anil.airreportbe.service;

import com.anil.airreportbe.Exception.NotFoundException;
import com.anil.airreportbe.model.Station;
import com.anil.airreportbe.model.entity.Pirep;
import com.anil.airreportbe.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anil.airreportbe.specification.PirepSpecification.getPirepQuery;
import static com.anil.airreportbe.specification.PirepSpecification.*;

@Service
public class PirepServiceV1 {

    private final StationService stationService;
    private final PirepRepository pirepRepository;
    private final IcingConditionRepository icingConditionRepository;
    private final SkyConditionRepository skyConditionRepository;
    private final TurbulenceConditionRepository turbulenceConditionRepository;
    private final QualityControlFlagsRepository qualityControlFlagsRepository;


    public PirepServiceV1(StationService stationService, PirepRepository pirepRepository, IcingConditionRepository icingConditionRepository, SkyConditionRepository skyConditionRepository, TurbulenceConditionRepository turbulenceConditionRepository, QualityControlFlagsRepository qualityControlFlagsRepository) {
        this.stationService = stationService;
        this.pirepRepository = pirepRepository;
        this.icingConditionRepository = icingConditionRepository;
        this.skyConditionRepository = skyConditionRepository;
        this.turbulenceConditionRepository = turbulenceConditionRepository;
        this.qualityControlFlagsRepository = qualityControlFlagsRepository;
    }

    public List<Pirep> getPirepReport(String code, Integer radialDistance, ZonedDateTime startTime, ZonedDateTime endTime, Boolean icingCondition, Boolean skyCondition, Boolean qualityControlCondition, Boolean turbulenceCondition, String type, String visibility, String ceilingBelow) {

        List<String> stations = getStations(code, radialDistance);

//        List<Pirep> pirepReports = pirepRepository.findAllByAircraftCodeInAndObservationTimeBetween(stations, startTime, endTime);

//        Specification<Pirep> specification = Specification
//                .where(getPirepQuery(stations, startTime, endTime, icingCondition, skyCondition, qualityControlCondition, turbulenceCondition, type, visibility, ceilingBelow))
//                .or(icingCondition ? getIcing(icingCondition) : null)
//                .or(skyCondition ? getSky(skyCondition) : null)
//                .or(qualityControlCondition ? getQualityControl(qualityControlCondition) : null)
//                .or(turbulenceCondition ? getTB(turbulenceCondition) : null);
//
//
//        List<Pirep> pirepReports = pirepRepository.findAll(specification);

        List<Pirep> pirepReports = pirepRepository.findAll(getPirepQuery(stations, startTime,  endTime,  icingCondition,  skyCondition,  qualityControlCondition,  turbulenceCondition, type, visibility,  ceilingBelow));

        if (Objects.requireNonNull(pirepReports).size() > 0) {
            return pirepReports;
        } else {
            throw new NotFoundException("Report not available for " + code + " location");
        }
    }


    private List<String> getStations(String code, Integer radialDistance) {
        Optional<Station> station = stationService.getStationInfo(code.toUpperCase());
        if (station.isPresent()) {
            if (radialDistance > 0) {
                return stationService.getStationInfo(radialDistance, station.get().getLongitude(), station.get().getLatitude()).stream().map(Station::getStation_id).map(s -> s.substring(1)).collect(Collectors.toList());
            } else {
                return Collections.singletonList(code);
            }
        } else {
            throw new NotFoundException("Airport Identifier not found with the Station ID: " + code);
        }
    }


    public Boolean findPirepReport(String code, Integer radialDistance, ZonedDateTime startTime, ZonedDateTime endTime) {
        List<String> stations = getStations(code, radialDistance);
        List<Pirep> pirepReports = pirepRepository.findAllByAircraftCodeInAndObservationTimeBetween(stations,startTime,endTime);
        return Objects.requireNonNull(pirepReports).size() <= 0;
    }
}
