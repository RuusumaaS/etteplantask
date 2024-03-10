/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

/**
 * Exception taht is thrown when Status is given a wrong kind of string, not open or closed.
 * @author RuusumaaS
 */
public class IllegalStatusException extends RuntimeException{

    
    public IllegalStatusException(String status) {
        super("Status has to be either open or closed. Given status was "+ status);
    }
}
