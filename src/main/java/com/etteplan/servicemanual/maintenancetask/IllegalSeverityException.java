/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

/**
 * Exception that gets thrown, when wrong kind of string is given to Severity.
 * @author RuusumaaS
 */
public class IllegalSeverityException extends RuntimeException{

    public IllegalSeverityException(String severity) {
        super("Severity must be uniportant, important or critical. All in lowercase."
                + "Given severity was " + severity);
    }
}
