/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import static com.etteplan.servicemanual.maintenancetask.Severity.values;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for maintenancetasks's status. Has two values OPEN and CLOSED. With JSONCreator and JSONValue
 * I define how to serialize/deserialize JSON to enum. Allows RequestBody to have enum in lowecase.
 * @author RuusumaaS
 */
public enum Status {
    OPEN("open"),
    CLOSED("closed");
    
    private final String status;

    Status(String status){
        this.status=status;
    }

    /**
     * Method to serialize Status to JSON.
     * @return 
     */
    @JsonValue
    public String getStatus() {
        return status;
    }

    /**
     * This deserializes the JSON to enum. Since OPEN and CLOSED have values "open" and "closed"
     * those are what we want. This function gets the JSON-value as string and checks if it
     * can be used as enum. Only acceptable ones are open and closed. Otherwise throws
     * IllegalStatusException.
     * @param value
     * @return 
     */
    @JsonCreator
    public static Status fromValue(String value) {
        
        for (Status status : values()) {     //Compares possible enum-values of Status to given value.
            String currentStatus = status.getStatus();
            
            
            if (currentStatus.equals(value)) {   //Returns status if value was valid.
                return status;
            }
        }

       throw new IllegalStatusException(value);
    }
}
