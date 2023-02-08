package com.anil.airreportbe.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
//    private String request_index;
//    private String data_source;
//    private String request;
//    private String errors;
//    private String warnings;
//    private String time_taken_ms;
    @XmlElement
    private List<Station> data;
//    private com.anil.airreportbe.model.Data data;

}
