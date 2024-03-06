/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author asus
 */
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    
}
