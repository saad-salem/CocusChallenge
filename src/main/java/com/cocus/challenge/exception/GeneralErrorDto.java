package com.cocus.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralErrorDto {
    private Date timestamp;
    private String exception;
    private String message;
    private Map<String, Object> params;
    private String stackTrace;
}
