/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 *
 * @author RuusumaaS
 */
@Entity
public class MaintenanceTask {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    
    private LocalDateTime registrationDate;
    
    @Enumerated(EnumType.STRING)
    private Severity severity;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private FactoryDevice factoryDevice;
    
    protected MaintenanceTask(){}
    
    public MaintenanceTask(FactoryDevice device, String description, LocalDateTime date,
            Severity severity, Status status){
        this.factoryDevice = device;
        this.description = description;
        this.registrationDate = date;
        this.severity = severity;
        this.status = status;
    }
    
    public Long getId(){
        return this.id;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public LocalDateTime getRegistrationDate(){
        return this.registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime date){
        this.registrationDate = date;
    }
    
    public Severity getSeverity(){
        return this.severity;
    }
    
    public void setSeverity(Severity severity){
        this.severity = severity;
    }
    
    public Status getStatus(){
        return this.status;
    }
    
    public void setStatus(Status status){
        this.status = status;
    }
    
    public FactoryDevice getDevice(){
        return this.factoryDevice;
    }
    
    public void setDevice(FactoryDevice device){
        this.factoryDevice = device;
    }
    
    
}
