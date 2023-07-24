package com.anil.airreportbe.service;

import com.anil.airreportbe.Exception.NotFoundException;
import com.anil.airreportbe.model.Response;
import com.anil.airreportbe.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class StationService {
    @Autowired
    private final RestTemplate restTemplate;

    private static final String US_STATION_CONSTANT_PREFIX = "K";

    @Value("${aviation_station_url}")
    private String url;
    private final String stationUrl;

    public StationService(RestTemplate restTemplate, @Value("${aviation_station_with_radial_distance}") String stationUrl) {
        this.restTemplate = restTemplate;
        this.stationUrl = stationUrl;
    }

    public Optional<Station> getStationInfo(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

//        Response response = restTemplate.getForObject(url, Response.class, getStationRequestParam(code));
//        System.out.println(response.toString());
        ResponseEntity<Response> response1 = restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        Response.class,
                        getStationRequestParam(code)
                );
        if (response1.getStatusCode() == HttpStatus.OK) {
            System.out.println(response1.getBody().toString());
        }
        if (Objects.requireNonNull(response1.getBody()).getData().size() > 0) {
            return response1.getBody().getData().stream().findFirst();
        } else {
            throw new NotFoundException("Station not present");
        }
    }

    private Map<String, Object> getStationRequestParam(final String code) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("dataSource", "stations");
        requestParam.put("requestType", "retrieve");
        requestParam.put("format", "xml");
        requestParam.put("stationString", US_STATION_CONSTANT_PREFIX + code.toUpperCase());
        return requestParam;
    }

    public List<Station> getStationInfo(Integer radialDistance, final double longitude, final double latitude) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Response> response1 = restTemplate
                .exchange(
                        stationUrl,
                        HttpMethod.GET,
                        entity,
                        Response.class,
                        getStationWithRadialDistance(radialDistance, longitude, latitude)
                );
        if (response1.getStatusCode() == HttpStatus.OK) {
            System.out.println(Objects.requireNonNull(response1.getBody()));
        } else {
            throw new RuntimeException("Station not present");
        }
        if (Objects.requireNonNull(response1.getBody()).getData().size() > 0) {
            return response1.getBody().getData();
        } else {
            throw new RuntimeException("Station not present");
        }
    }

    private Map<String, Object> getStationWithRadialDistance(final Integer radialDistance, final double longitude, final double latitude) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("dataSource", "stations");
        requestParam.put("requestType", "retrieve");
        requestParam.put("format", "xml");
        requestParam.put("radialDistance", String.format("%d;%.2f,%.2f", radialDistance, longitude, latitude));
        return requestParam;
    }

}
