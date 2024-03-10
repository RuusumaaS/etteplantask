/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

/**
 * If post-request doesn't have description, this gets thrown.
 * @author RuusumaaS
 */
public class DescriptionNotFoundException  extends RuntimeException{

    
    public DescriptionNotFoundException() {
        super("Maintenancetask needs a description.");
    }
}
