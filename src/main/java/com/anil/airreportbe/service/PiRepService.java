package com.anil.airreportbe.service;

import com.anil.airreportbe.model.AircraftReport;
import com.anil.airreportbe.model.PiRepResponse;
import com.anil.airreportbe.model.Station;
import com.anil.airreportbe.model.entity.Pirep;
import com.anil.airreportbe.repository.PirepRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PiRepService {

    private final RestTemplate restTemplate;
    private final StationService stationService;
    private static final String PIREP = "PIREP";

    private final PirepRepository pirepRepository;

    @Value("${aviation_pirep_url}")
    private String url;

    @Value("${aviation_url}")
    private String urll;

    public PiRepService(RestTemplate restTemplate, StationService stationService, PirepRepository pirepRepository) {
        this.restTemplate = restTemplate;
        this.stationService = stationService;
        this.pirepRepository = pirepRepository;
    }

    public List<AircraftReport> getPiRepData(String code) {
        Optional<Station> station = stationService.getStationInfo(code);
        var longitude = station.get().getLongitude();
        var latitude = station.get().getLatitude();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PiRepResponse> response = restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        PiRepResponse.class,
                        getPiRepRequestParam(longitude, latitude)
                );
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody().toString());
        }
        if (Objects.requireNonNull(response.getBody()).getData().size() > 0) {
            return response.getBody().getData().stream()
                    .filter(aircraftReport -> Objects.equals(aircraftReport.getReport_type(), PIREP))
                    .filter(ar -> Objects.equals(ar.getRaw_text().substring(0, 3), code))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Station not present");
        }
    }

    private Map<String, Object> getPiRepRequestParam(final double longitude, final double latitude) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("dataSource", "aircraftreports");
        requestParam.put("requestType", "retrieve");
        requestParam.put("format", "xml");
        requestParam.put("hoursBeforeNow", 2);
        requestParam.put("radialDistance", String.format("200;%f,%f", longitude, latitude));
        return requestParam;
    }

    public List<AircraftReport> getPiReportData(String code, String startTime, String endTime) {
        Optional<Station> station = stationService.getStationInfo(code);
        var longitude = station.get().getLongitude();
        var latitude = station.get().getLatitude();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUri(URI.create(urll))
                .queryParams(getPiRepRequestQueryParam(longitude, latitude, startTime, endTime))
                .build().toUri();
        System.out.println(uri.toString());

        ResponseEntity<PiRepResponse> response = restTemplate
                .exchange(
                        uri,
                        HttpMethod.GET,
                        entity,
                        PiRepResponse.class
                );
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println(response.getBody().toString());
        }
        if (Objects.requireNonNull(response.getBody()).getData().size() > 0) {
            return response.getBody().getData().stream()
                    .filter(aircraftReport -> Objects.equals(aircraftReport.getReport_type(), PIREP))
                    .filter(ar -> Objects.equals(ar.getRaw_text().substring(0, 3), code))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Report not available for " + code + " location");
        }
    }

    private Map<String, Object> getPiRepRequestParam(final double longitude, final double latitude, final String startTime, final String endTime) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("dataSource", "aircraftreports");
        requestParam.put("requestType", "retrieve");
        requestParam.put("format", "xml");
        requestParam.put("hoursBeforeNow", 2);
        requestParam.put("radialDistance", String.format("20;%f,%f", longitude, latitude));
        requestParam.put("startTime", startTime);
        requestParam.put("endTime", endTime);
        return requestParam;
    }

    private MultiValueMap<String, String> getPiRepRequestQueryParam(final double longitude, final double latitude, final String startTime, final String endTime) {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("dataSource", "aircraftreports");
        requestParam.add("requestType", "retrieve");
        requestParam.add("format", "xml");
        requestParam.add("radialDistance", String.format("20;%.2f,%.2f", longitude, latitude));
        if (null != startTime && startTime.length() > 0 && null != endTime && endTime.length() > 0) {
            requestParam.add("startTime", startTime);
            requestParam.add("endTime", endTime);
        } else {
            requestParam.add("hoursBeforeNow", "2");
        }
        return requestParam;
    }


    /*from repository */

    public List<Pirep> getPirepReport(String code, ZonedDateTime startTime, ZonedDateTime endTime, boolean icingCondition, boolean skyCondition, boolean qualityControlCondition, boolean turbulenceCondition, String visibility, String ceilingBelow) {
        Optional<Station> station = stationService.getStationInfo(code);
        List<Pirep> pirepReports = pirepRepository.findAllByAircraftCodeAndObservationTimeBetween(code,startTime,endTime);

//        List<Pirep> pirepReport = pirepRepository.findAllByAircraftCodeAndObservationTimeBetween(code,startTime,endTime);

        if (Objects.requireNonNull(pirepReports).size() > 0) {
            return pirepReports;
        } else {
            throw new RuntimeException("Report not available for " + code + " location");
        }
    }
}
