package com.anil.airreportbe.service;

import com.anil.airreportbe.model.Response;
import com.anil.airreportbe.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class StationService {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${aviation_station_url}")
    private String url;

    public StationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
            throw new RuntimeException("Station not present");
        }
    }

    private Map<String, Object> getStationRequestParam(final String code) {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("dataSource", "stations");
        requestParam.put("requestType", "retrieve");
        requestParam.put("format", "xml");
        requestParam.put("stationString", code);
        return requestParam;
    }
}
