/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author asus
 */
@RestController
@Api(tags = "MaintenanceTaskController", description = "Adding, querying, deleting and modyfying maintenance tasks.")
public class MaintenanceTaskController {
    
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final FactoryDeviceRepository factoryDeviceRepository;
    
    //These are default values for severity and status in case user doesn't provide these
    //in their request's body. They are only used when creating a new maintnance task.
    private static final Severity DEFAULT_SEVERITY = Severity.UNIMPORTANT;
    private static final Status DEFAULT_STATUS = Status.OPEN;

    MaintenanceTaskController(MaintenanceTaskRepository maintenanceTaskRepository, FactoryDeviceRepository factoryDeviceRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.factoryDeviceRepository = factoryDeviceRepository;
    }
    
    /**
     * Searches all maintenance tasks first ordered by severity and then registration time.
     * @return List of all MaintenanceTasks
     */
    @ApiOperation(value = "Get all maintenance tasks", notes = "List of tasks which is first orderd by severity and then registartiom date")
    @GetMapping("/maintenancetasks")
    List<MaintenanceTask> allMaintenanceTasks() {
        return maintenanceTaskRepository.findAllByOrderBySeverityDescRegistrationDateDesc();
    }

    /**
     * Find one MaintenanceTask with given id.
     * @param id
     * @return MaintenanceTask
     * @throws MaintenanceTaksNotFoundException
     */
    @ApiOperation(value = "Get one maintenance tasks by given id", notes ="Get on task")
    @GetMapping("/maintenancetasks/{id}")
    MaintenanceTask oneMaintenanceTask(@PathVariable Long id) {
        return maintenanceTaskRepository.findById(id)
            .orElseThrow(() -> new MaintenanceTaskNotFoundException(id));
    }
    
    /**
     * Finds all maintenancetasks filtered by factorydevice.
     * @param factoryDeviceId
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks and filter it with factorydevice's id.")
    @GetMapping("/maintenancetasks/factorydevice/{factoryDeviceId}")
    List<MaintenanceTask> tasksFilteredByDevices(@PathVariable Long factoryDeviceId){
        return maintenanceTaskRepository.findByFactoryDeviceIdOrderBySeverityDescRegistrationDateDesc(factoryDeviceId);
    }
    
    /**
     * Find all maintenancetasks filtered with status. So either all open tasks or closed ones.
     * @param status
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks filtered with status", notes ="Good way to aget either all open tasks or closed ones.")
    @GetMapping("maintenancetasks/status/{status}")
    List<MaintenanceTask> findTasksByStatus(@PathVariable String status){
        return maintenanceTaskRepository.findByStatusOrderBySeverityDescRegistrationDateDesc(Status.valueOf(status.toUpperCase()));
    }
    
    /**
     * Find all maintenancetasks filtered with severity.
     * @param severity
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks", notes ="")
    @GetMapping("maintenancetasks/severity/{severity}")
    List<MaintenanceTask> findTasksBySeverity(@PathVariable String severity){
        return maintenanceTaskRepository.findBySeverityOrderByRegistrationDateDesc(Severity.valueOf(severity.toUpperCase()));
    }

    /**
     * Function that creates a new MaintnenaceTask. Doesn't require all field in RequestBody.
     * In order to save new task. RequestBody must contain description and path must contain
     * exsiting id for FactoryDevices. If severity and status are not given, task will
     * be given default values of status and severity.
     * @param task
     * @param factoryDeviceId
     * @return 
     */
    @PostMapping("/maintenancetasks/{factoryDeviceId}")
    MaintenanceTask newMaintenanceTask(@RequestBody MaintenanceTask task,@PathVariable Long factoryDeviceId){
        
        
        if(task.getDescription() == null){
            throw new DescriptionNotFoundException();   //Description can't be null like severity and status can.
        }
        
        if(task.getSeverity() == null){
            task.setSeverity(DEFAULT_SEVERITY);
        }
        
        if(task.getStatus() == null){
            task.setStatus(DEFAULT_STATUS);
        }
        
        FactoryDevice device = factoryDeviceRepository.findById(factoryDeviceId)
                .orElseThrow(() -> new FactoryDeviceNotFoundException(task.getDevice().getId()));
        
        task.setDevice(device);
        
        task.setRegistrationDate(LocalDateTime.now());
        
        return maintenanceTaskRepository.save(task);
    }
    
    /**
     * Put-request to modify tasks. Has two paths, one with factorydeviceid if user wants
     * to change task's factorydevice and one without factorydeviceid. This only changes
     * the fields that were given in requestbody by checking if given field is null or not.
     * @param task
     * @param id
     * @param factoryDeviceId
     * @return 
     */
    @PutMapping({"/maintenancetasks/{id}/{factoryDeviceId}","/maintenancetasks/{id}"})
    MaintenanceTask modifyMaintenanceTaskDevice(@RequestBody MaintenanceTask task,
            @PathVariable Long id, @PathVariable(required = false) Long factoryDeviceId){
        
        return maintenanceTaskRepository.findById(id).      //Find task or throw error.
                map(maintenancetask -> {
                    
                    if(factoryDeviceId != null){  //Trying to find existing FactoryDevice. If not found, error.
                        FactoryDevice factoryDevice = factoryDeviceRepository.findById(factoryDeviceId)
                            .orElseThrow(() -> new FactoryDeviceNotFoundException(task.getDevice().getId()));
                        maintenancetask.setDevice(factoryDevice);
                    }
                    
                    
                    if(task.getDescription() != null){
                        maintenancetask.setDescription(task.getDescription());
                    }
                    if(task.getSeverity() != null){
                        maintenancetask.setSeverity(task.getSeverity());
                    }
                    if(task.getStatus() != null){
                        maintenancetask.setStatus(task.getStatus());
                    }
                    
                    //If you want to update the registration date to match update moment, uncomment the line below.
                    //maintenancetask.setRegistrationDate(LocalDateTime.now());
                    
                    return maintenancetask;
                }).orElseThrow(()-> new MaintenanceTaskNotFoundException(id));
    }
    
    
    /**
     * Delete MaintenanceTask if given correct id.
     * @param id 
     */
    @DeleteMapping("/maintenancetasks/{id}")
    void deleteMaintenanceTask(@PathVariable Long id){
        if(!maintenanceTaskRepository.existsById(id)){
            throw new MaintenanceTaskNotFoundException(id);
        }
        maintenanceTaskRepository.deleteById(id);
    }
    
    
}
