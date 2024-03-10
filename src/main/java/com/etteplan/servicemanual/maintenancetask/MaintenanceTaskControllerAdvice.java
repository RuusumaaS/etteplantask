/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MaintenanceTaskControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler(MaintenanceTaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String MaintenanceTaskNotFoundHandler(MaintenanceTaskNotFoundException ex){
        return ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(IllegalStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IllegalStatusHandler(IllegalStatusException ex){
        return ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(IllegalSeverityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IllegalSeverityHandler(IllegalSeverityException ex){
        return ex.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(DescriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String IllegalStatusHandler(DescriptionNotFoundException ex){
        return ex.getMessage();
    }
    
    
}
