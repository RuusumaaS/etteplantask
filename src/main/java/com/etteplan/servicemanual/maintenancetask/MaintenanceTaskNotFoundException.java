/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

/**
 *
 * @author asus
 */
public class MaintenanceTaskNotFoundException extends RuntimeException{

    /**
     * Constructs an instance of <code>MaintenanceTaskNotFound</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MaintenanceTaskNotFoundException(Long id) {
        super("Could not find maintenance task " + id);
    }
}
