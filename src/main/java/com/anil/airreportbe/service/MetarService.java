package com.anil.airreportbe.service;

import com.anil.airreportbe.Exception.NotFoundException;
import com.anil.airreportbe.model.Airport;
import com.anil.airreportbe.model.Station;
import com.anil.airreportbe.model.entity.Metar;
import com.anil.airreportbe.repository.MetarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javafx.util.Pair;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anil.airreportbe.specification.MetarSpecification.getMetarQueryWithJoin;
import static com.anil.airreportbe.specification.MetarSpecification.getMetarQueryWithJoinNew;
import static com.anil.airreportbe.util.Constants.TIME_RANGES;

@Service
@Slf4j
public class MetarService {
    private final StationService stationService;
    private final MetarRepository metarRepository;

    private final PirepServiceV1 pirepServiceV1;

    private final AirportService airportService;

    public MetarService(StationService stationService, MetarRepository metarRepository, PirepServiceV1 pirepServiceV1, AirportService airportService) {
        this.stationService = stationService;
        this.metarRepository = metarRepository;
        this.pirepServiceV1 = pirepServiceV1;
        this.airportService = airportService;
    }

    @Cacheable(value = "metarReports", key = "{#code, #radialDistance, #startTime, #endTime, #isPirepMissing, #isPirepCondition, #type}")
    public List<Metar> getMetarReports(String code, Integer radialDistance, ZonedDateTime startTime, ZonedDateTime endTime, Boolean isPirepMissing, Boolean isPirepCondition, String type) {
//        List<String> stations = getStations(code, radialDistance);
        List<String> stations = getStationsFromCSV(code, radialDistance);
//        List<Metar> metarReports = metarRepository.findAllByAircraftCodeInAndAircraftTypeAndObservationTimeBetween(stations, type, startTime, endTime);
        List<Metar> metarReports = metarRepository.findAll(getMetarQueryWithJoinNew(stations, startTime, endTime, isPirepCondition, isPirepMissing, type));

        if (isPirepMissing) {
            metarReports = metarReports.stream().parallel().filter(m -> {
                Pair<ZonedDateTime, ZonedDateTime> result = getZonedDateTimeRange(m.getObservationTime());
                assert result != null;
                return pirepServiceV1.findPirepReport(code, 0, result.getKey(), result.getValue());
            }).toList();
        }


        if (Objects.requireNonNull(metarReports).size() > 0) {
            return metarReports;
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

    public static Pair<ZonedDateTime, ZonedDateTime> getZonedDateTimeRange(ZonedDateTime checkDateTime) {
        LocalTime checkTime = checkDateTime.toLocalTime();
        LocalTime mid = LocalTime.parse("00:00:00");

        for (String[] timeRange : TIME_RANGES) {
            LocalTime start = LocalTime.parse(timeRange[0]);
            LocalTime end = LocalTime.parse(timeRange[1]);

            switch (checkTime.compareTo(start)) {
                case 0, 1 -> {
                    if (checkTime.compareTo(end) <= 0) {
                        ZonedDateTime startDateTime;
                        if (start.compareTo(mid) == 0) {
                            startDateTime = checkDateTime.minusDays(1).with(LocalTime.parse("23:30:00"));
                        } else {
                            startDateTime = checkDateTime.with(start);
                        }
                        ZonedDateTime endDateTime = checkDateTime.with(end);

                        return new Pair<>(startDateTime, endDateTime);
                    }
                }
            }
        }
        return null;
    }

    private List<String> getStationsFromCSV(String code, Integer radialDistance) {
        Optional<Airport> station = airportService.getAirportInfo(code.toUpperCase());
        if (station.isPresent()) {
            if (radialDistance > 0) {
                return airportService.findNearestAirports(airportService.getAirportsInfoFromCSVFile(), station.get(), radialDistance).stream().map(Airport::getIata).collect(Collectors.toList());
            } else {
                return Collections.singletonList(code);
            }
        } else {
            throw new NotFoundException("Airport Identifier not found with the Station ID: " + code);
        }
    }

//    New code after optimize the code

    @Cacheable(value = "metarReports", key = "{#code, #radialDistance, #startTime, #endTime, #isPirepMissing, #isPirepCondition, #type}")
    public List<Metar> getMetarReportsNew(String code, Integer radialDistance, ZonedDateTime startTime, ZonedDateTime endTime, Boolean isPirepMissing, Boolean isPirepCondition, String type) {
        List<String> stations = getStationsFromCSVNew(code, radialDistance)
                .orElseThrow(() -> new NotFoundException("Airport Identifier not found with the Station ID: " + code));
        long start = System.currentTimeMillis(); // Capture start time
        List<Metar> metarReports = metarRepository.findAll(getMetarQueryWithJoinNew(stations, startTime, endTime, isPirepCondition, isPirepMissing, type));
        long end = System.currentTimeMillis(); // Capture end time

        // Calculate and print the execution time
        long executionTime = end - start;
        log.info("Execution time of getMetarReportsNew method: {} milliseconds", executionTime);


        if (isPirepMissing) {
            metarReports = metarReports.stream().filter(m -> {
                ZonedDateTime[] result = getZonedDateTimeRangeNew(m.getObservationTime());
                if (result != null) {
                    return pirepServiceV1.findPirepReport(code, 0, result[0], result[1]);
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
        }

        if (metarReports.isEmpty()) {
            throw new NotFoundException("Report not available for " + code + " location");
        }

        return metarReports;
    }

    private Optional<List<String>> getStationsFromCSVNew(String code, Integer radialDistance) {
        Optional<Airport> station = airportService.getAirportInfo(code.toUpperCase());
        if (station.isPresent()) {
            if (radialDistance > 0) {
                return Optional.of(airportService.findNearestAirports(airportService.getAirportsInfoFromCSVFile(), station.get(), radialDistance).stream().map(Airport::getIata).collect(Collectors.toList()));
            } else {
                return Optional.of(Collections.singletonList(code));
            }
        } else {
            return Optional.empty();
        }
    }

    public static ZonedDateTime[] getZonedDateTimeRangeNew(ZonedDateTime checkDateTime) {
        LocalTime checkTime = checkDateTime.toLocalTime();
        LocalTime mid = LocalTime.parse("00:00:00");

        for (String[] timeRange : TIME_RANGES) {
            LocalTime start = LocalTime.parse(timeRange[0]);
            LocalTime end = LocalTime.parse(timeRange[1]);

            if (checkTime.compareTo(start) >= 0 && checkTime.compareTo(end) <= 0) {
                ZonedDateTime startDateTime;
                if (start.compareTo(mid) == 0) {
                    startDateTime = checkDateTime.minusDays(1).with(LocalTime.parse("23:30:00"));
                } else {
                    startDateTime = checkDateTime.with(start);
                }
                ZonedDateTime endDateTime = checkDateTime.with(end);

                return new ZonedDateTime[]{startDateTime, endDateTime};
            }
        }
        return null;
    }
}
