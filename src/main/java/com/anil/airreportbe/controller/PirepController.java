package com.anil.airreportbe.controller;

import com.anil.airreportbe.model.AircraftReport;
import com.anil.airreportbe.model.entity.Pirep;
import com.anil.airreportbe.repository.PirepRepository;
import com.anil.airreportbe.service.PiRepService;
import com.anil.airreportbe.service.PirepServiceV1;
import com.anil.airreportbe.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/pireps")
@Slf4j
public class PirepController {

    private final PirepRepository pirepRepository;
    private final JsonUtil jsonUtil;

    private final PirepServiceV1 pirepServiceV1;

    public PirepController(PirepRepository pirepRepository, JsonUtil jsonUtil, PirepServiceV1 pirepServiceV1) {
        this.pirepRepository = pirepRepository;
        this.jsonUtil = jsonUtil;
        this.pirepServiceV1 = pirepServiceV1;
    }

    @GetMapping("/getAll")
    public List<Pirep> getPireps() {
        return (List<Pirep>) pirepRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Pirep getPireps(@PathVariable(name = "id") final Long id) {
        Pirep pirep = pirepRepository.findById(id).get();
        log.info(pirep.toString());
//        return jsonUtil.convertObjectToJsonString(pirep);
        return pirep;
    }

    @GetMapping(value = "/db/{code}", produces = "application/json")
    public List<Pirep> getPiReport(@PathVariable(name = "code") String code,
                                   @RequestParam(name = "radialDistance", required = false, defaultValue = "0") Integer radialDistance,
                                   @RequestParam(name = "startTime", required = false) ZonedDateTime startTime,
                                   @RequestParam(name = "endTime", required = false) ZonedDateTime endTime,
                                   @RequestParam(name = "icingCondition", required = false, defaultValue = "false") Boolean icingCondition,
                                   @RequestParam(name = "skyCondition", required = false, defaultValue = "false") Boolean skyCondition,
                                   @RequestParam(name = "qualityControlCondition", required = false, defaultValue = "false") Boolean qualityControlCondition,
                                   @RequestParam(name = "turbulenceCondition", required = false, defaultValue = "false") Boolean turbulenceCondition,
                                   @RequestParam(name = "type", required = false, defaultValue = "PIREP") String type,
                                   @RequestParam(name = "visibility", required = false) String visibility,
                                   @RequestParam(name = "ceilingBelow", required = false) String ceilingBelow) {

        List<Pirep> aircraftReport = pirepServiceV1.getPirepReport(code, radialDistance, startTime, endTime, icingCondition, skyCondition, qualityControlCondition, turbulenceCondition, type, visibility, ceilingBelow);

        return aircraftReport;
    }

}
