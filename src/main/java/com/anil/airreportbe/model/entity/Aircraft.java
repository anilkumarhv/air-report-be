package com.anil.airreportbe.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Aircraft {
    String code;
    String type;
}
