/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
 * MaintenanceTask entity. Task has Id,description, date, severity, status and factorydevice.
 * @author RuusumaaS
 */
@ApiModel(description = "User details")
@Entity
public class MaintenanceTask {
    @ApiModelProperty(notes = "The unique ID of the maintenance task. Get automatically generated")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ApiModelProperty(notes = "Description of the task as String. Needs to given when creating a new task.")
    private String description;
    
    @ApiModelProperty(notes = "The time when the task is registered. Gets automatically added.")
    private LocalDateTime registrationDate;
    
    @ApiModelProperty(notes = "Severity of the task. Can only have three values: CRITICAL, IMPORTANT and UNIMPORTANT. If not given, automatically added as UNIMPORTANT")
    @Enumerated(EnumType.STRING)
    private Severity severity;         //enum's in postgres. Shows value as string in database.
    
    @ApiModelProperty(notes = "Status of the task. Can only be either OPEN or CLOSED. If not given, automatically added as OPEN")
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ApiModelProperty(notes = "The factory device that the maintenance task is assigned to.")
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private FactoryDevice factoryDevice;     //In the database, device's id is the foreign key.
    
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
