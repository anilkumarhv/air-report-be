package com.anil.airreportbe.controller;

import com.anil.airreportbe.model.AircraftReport;
import com.anil.airreportbe.model.PiRepResponse;
import com.anil.airreportbe.model.Station;
import com.anil.airreportbe.service.PiRepService;
import com.anil.airreportbe.service.StationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pirep")
public class PiReportController {

    private final StationService stationService;
    private final PiRepService piRepService;

    public PiReportController(StationService stationService, PiRepService piRepService) {
        this.stationService = stationService;
        this.piRepService = piRepService;
    }

    @GetMapping("/station/{code}")
    public String getStation(@PathVariable(name = "code", required = true) String code) {
        Optional<Station> station = stationService.getStationInfo(code);
        return station.get().toString();

    }

//    @GetMapping("/{code}")
//    public String getPirep(@PathVariable(name = "code", required = true) String code) throws JsonProcessingException {
//        List<AircraftReport> aircraftReport = piRepService.getPiRepData(code);
//        ObjectMapper objectMapper = new ObjectMapper();
//        var jsonString = objectMapper.writeValueAsString(aircraftReport);
//        System.out.println(jsonString);
//
////        return aircraftReport;
//        return jsonString;
//    }

    @GetMapping("/{code}")
    public String getPiReport(@PathVariable(name = "code") String code,
                                            @RequestParam(name = "startTime", required = false) String startTime,
                                            @RequestParam(name = "endTime", required = false) String endTime
    ) throws JsonProcessingException {

        List<AircraftReport> aircraftReport = piRepService.getPiReportData(code, startTime, endTime);
        ObjectMapper objectMapper = new ObjectMapper();
        var jsonString = objectMapper.writeValueAsString(aircraftReport);
        System.out.println(jsonString);

        return jsonString;
    }
}
