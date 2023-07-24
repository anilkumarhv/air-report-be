package com.anil.airreportbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AirReportBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirReportBeApplication.class, args);
	}

}
