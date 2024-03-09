/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for maintenancetasks's severity. Three values CRITICAL, IMPORTANT and UNIMPORTANT. With JSONCreator and JSONValue
 * I define how to serialize/deserialize JSON to enum. Allows RequestBody to have enum in lowecase.
 * @author RuusumaaS
 */
public enum Severity {
    CRITICAL("critical"),
    IMPORTANT("important"),
    UNIMPORTANT("unimportant");
    
    
    private final String severity;

    Severity(String severity){
        this.severity=severity;
    }

    
    /**
     * Method to serialize Status to JSON.
     * @return 
     */
    @JsonValue
    public String getSeverity() {
        return severity;
    }

    
    /**
     * This deserializes the JSON to enum. I gave enum values new values which are values as string in lowercase.
     * This function gets the JSON-value as string and checks if it can be used as enum. 
     * Only acceptable ones are critical, important and unimportant. Otherwise throws IllegalSeverityException.
     * @param value
     * @return 
     */
    @JsonCreator
    public static Severity fromValue(String value) {
        
        for (Severity severity : values()) {   //Possible values of Severity
            String currentSeverity = severity.getSeverity();
            
            if (currentSeverity.equals(value)) {   //If valid severity is given in JSON, severity is returned.
                return severity;
            }
        }

        
       throw new IllegalSeverityException(value);
    }
}
