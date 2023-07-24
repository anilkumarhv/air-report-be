package com.anil.airreportbe.util;

import com.anil.airreportbe.response.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class AppUtil {

    public static <T> ResponseEntity<AppResponse> errorResponse(HttpStatus status, String errMsg) {
        AppResponse<T> ar = new AppResponse<>(status);
        ar.setMessage(errMsg);
        ar.setStatus(true);
//        ar.getTime();
        ar.getTimestamp();
        return new ResponseEntity<>(ar, status);
    }
}
