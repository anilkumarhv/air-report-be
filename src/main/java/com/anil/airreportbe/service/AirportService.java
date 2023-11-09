package com.anil.airreportbe.service;

import com.anil.airreportbe.Exception.CustomException;
import com.anil.airreportbe.Exception.NotFoundException;
import com.anil.airreportbe.model.Airport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private final DistanceCalculatorService distanceCalculatorService;
    private final ObjectMapper objectMapper;

    private static final String US_STATION_CONSTANT_PREFIX = "K";

    public AirportService(DistanceCalculatorService distanceCalculatorService, ObjectMapper objectMapper) {
        this.distanceCalculatorService = distanceCalculatorService;
        this.objectMapper = objectMapper;
    }

    public Map<String, Airport> getAirportsInfoFromCSVFile() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(new FileInputStream("stations.json"), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new CustomException("Exception while reading Stations information from CSV");
        }
    }

    public Optional<Airport> getAirportInfo(final String iataCode) {
        return Optional.ofNullable(getAirportsInfoFromCSVFile().get(US_STATION_CONSTANT_PREFIX + iataCode));
    }

    public Collection<Airport> findNearestAirports(Map<String, Airport> airportMap, Airport referenceAirport, double maxDistance) {
        return airportMap.values()
                .stream()
                .filter(airport -> airport != referenceAirport) // Exclude the reference airport
                .filter(airport -> airport.getIata() != null)
                .filter(airport -> distanceCalculatorService.calculateDistance(referenceAirport, airport) <= maxDistance)
                .collect(Collectors.toList());
    }
}
