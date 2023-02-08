package com.anil.airreportbe;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class MainTestClass {
    public static void main(String[] args) {
//        Date date = DateTimeFormatter.ISO_INSTANT.parse("2023-02-05T15:17:33Z");
        Instant instant= new Date().toInstant().atZone(ZoneId.systemDefault()).toInstant();
        System.out.println(instant);
        System.out.println(new Date().toInstant());
    }
}
