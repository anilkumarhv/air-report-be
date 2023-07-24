package com.anil.airreportbe.controller;

import com.anil.airreportbe.model.entity.Metar;
import com.anil.airreportbe.service.MetarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/metar")
@Slf4j
public class MetarController {

    private final MetarService metarService;

    public MetarController(MetarService metarService) {
        this.metarService = metarService;
    }

    @GetMapping(value = "/db/{code}", produces = "application/json")
    public ResponseEntity<List<Metar>> getPiReport(@PathVariable(name = "code") String code,
                                                   @RequestParam(name = "radialDistance", required = false, defaultValue = "0") Integer radialDistance,
                                                   @RequestParam(name = "startTime", required = false) ZonedDateTime startTime,
                                                   @RequestParam(name = "endTime", required = false) ZonedDateTime endTime,
                                                   @RequestParam(name = "isPirepMissing", required = false, defaultValue = "false") Boolean isPirepMissing,
                                                   @RequestParam(name = "isPirepCondition", required = false, defaultValue = "false") Boolean isPirepCondition,
                                                   @RequestParam(name = "type", required = false, defaultValue = "METAR") String type) {

        List<Metar> metarReports = metarService.getMetarReports(code, radialDistance, startTime, endTime, isPirepMissing, isPirepCondition, type);

        return new ResponseEntity<>(metarReports, HttpStatus.OK);
    }
}
