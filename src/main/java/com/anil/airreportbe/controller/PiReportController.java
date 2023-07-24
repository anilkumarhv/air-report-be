package com.anil.airreportbe.controller;

import com.anil.airreportbe.model.AircraftReport;
import com.anil.airreportbe.model.Station;
import com.anil.airreportbe.service.PiRepService;
import com.anil.airreportbe.service.StationService;
import com.anil.airreportbe.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pirep")
public class PiReportController {

    private final StationService stationService;
    private final PiRepService piRepService;

    private final JsonUtil jsonUtil;

    public PiReportController(StationService stationService, PiRepService piRepService, JsonUtil jsonUtil) {
        this.stationService = stationService;
        this.piRepService = piRepService;
        this.jsonUtil = jsonUtil;
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
                                            @RequestParam(name = "endTime", required = false) String endTime) throws JsonProcessingException {

        List<AircraftReport> aircraftReport = piRepService.getPiReportData(code, startTime, endTime);

//        if (aircraftReport.size()>0){
            return jsonUtil.convertObjectToJsonString(aircraftReport);
//        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        var jsonString = objectMapper.writeValueAsString(aircraftReport);
//        System.out.println(jsonString);
//
//        return jsonString;
    }
}
