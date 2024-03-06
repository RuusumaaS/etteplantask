/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 *
 * @author RuusumaaS
 */
@Entity
public class MaintenanceTask {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String description;
    private Date registration;
    private Severity severity;
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private FactoryDevice factoryDevice;
    
    protected MaintenanceTask(){}
    
    public MaintenanceTask(FactoryDevice device, String description, Date date,
            Severity severity, Status status){
        this.factoryDevice = device;
        this.description = description;
        this.registration = date;
        this.severity = severity;
        this.status = status;
    }
    
    public Long getId(){
        return this.id;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public Date getRegistrationDate(){
        return this.registration;
    }
    
    public Severity getSeverity(){
        return this.severity;
    }
    
    public Status getStatus(){
        return this.status;
    }
    
    public FactoryDevice getDevice(){
        return this.factoryDevice;
    }
    
    
}
