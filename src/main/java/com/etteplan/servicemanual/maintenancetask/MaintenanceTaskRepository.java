/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author asus
 */
public interface MaintenanceTaskRepository extends JpaRepository<MaintenanceTask, Long> {
    
    /**
     * Custom query to find all tasks first ordered by severity and then registration.
     * @return 
     */
    public List<MaintenanceTask> findAllByOrderBySeverityAscRegistrationDateDesc();
    
    /**
     * Custom query to find maintenancetasks assigned to specific device.
     * @param factorydeviceId
     * @return 
     */
    public List<MaintenanceTask> findByFactoryDeviceIdOrderBySeverityAscRegistrationDateDesc(Long factorydeviceId);
    
    /**
     * Custom query that filters with given status.
     * @param status
     * @return 
     */
    public List<MaintenanceTask> findByStatusOrderBySeverityAscRegistrationDateDesc(Status status);
    
    /**
     * Custom query that filters with given severity.
     * @param status
     * @return 
     */
    public List<MaintenanceTask> findBySeverityOrderByRegistrationDateDesc(Severity severity);
}
