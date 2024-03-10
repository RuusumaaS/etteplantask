/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to recieve the http-requests and define the api paths. 
 * Controller handles the requests depending on the endpoint, calls function to do the right
 * actions. Only modifies Maintenance tasks but needs access to facotry devices.
 * I have defined here the default values for severity and status.
 * @author RuusumaaS 
 */
@RestController
@Api(tags = "MaintenanceTaskController", description = "Adding, querying, deleting and modifying maintenance tasks.")
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
        return maintenanceTaskRepository.findAllByOrderBySeverityAscRegistrationDateDesc();
    }

    /**
     * Finds one MaintenanceTask with given id or throws error if given id
     * doesn't exist.
     * @param id
     * @return MaintenanceTask
     * @throws MaintenanceTaksNotFoundException
     */
    @ApiOperation(value = "Get one maintenance tasks by given id")
    @GetMapping("/maintenancetasks/{id}")
    MaintenanceTask oneMaintenanceTask(@PathVariable Long id) {
        return maintenanceTaskRepository.findById(id)
            .orElseThrow(() -> new MaintenanceTaskNotFoundException(id));
    }
    
    /**
     * Finds all maintenancetasks filtered by factorydevice if device exists.
     * @param factoryDeviceId
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks and filter it with factorydevice's id.")
    @GetMapping("/maintenancetasks/factorydevice/{factoryDeviceId}")
    List<MaintenanceTask> tasksFilteredByDevices(@PathVariable Long factoryDeviceId){
        if(!factoryDeviceRepository.existsById(factoryDeviceId)){
            throw new FactoryDeviceNotFoundException(factoryDeviceId);
        }
        return maintenanceTaskRepository.findByFactoryDeviceIdOrderBySeverityAscRegistrationDateDesc(factoryDeviceId);
    }
    
    /**
     * Find all maintenancetasks filtered with status. So either all open tasks or closed ones. 
     * If given inappropriate status, Status enmu automatically throws an error.
     * @param status
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks filtered with status", notes ="Good way to get either all open tasks or closed ones.")
    @GetMapping("maintenancetasks/status/{status}")
    List<MaintenanceTask> findTasksByStatus(@PathVariable String status){
        //fromValue is defined in Status.java. Way to create a status from a string. Thwors IllegalStatusException.
        return maintenanceTaskRepository.findByStatusOrderBySeverityAscRegistrationDateDesc(Status.fromValue(status));
    }
    
    /**
     * Find all maintenancetasks filtered with severity. If given inappropriate severity, 
     * Severity enum automatically throws an error.
     * @param severity
     * @return 
     */
    @ApiOperation(value = "Get all maintenance tasks filtered with given severity.", notes ="")
    @GetMapping("maintenancetasks/severity/{severity}")
    List<MaintenanceTask> findTasksBySeverity(@PathVariable String severity){
        return maintenanceTaskRepository.findBySeverityOrderByRegistrationDateDesc(Severity.fromValue(severity));
    }

    /**
     * Function that creates a new MaintnenaceTask. Doesn't require all field in RequestBody.
     * In order to save new task. RequestBody must contain description and path must contain
     * exsiting id for FactoryDevices. If severity and status are not given, task will
     * be given default values defined earlier. Severity and status enum sends exceptions
     * if they get wrong kind of string. This functions gives hte new task registration time.
     * @param task
     * @param factoryDeviceId
     * @return 
     */
    @ApiOperation(value = "Create a new task",
            notes = "This endpoint creates a new task with the given information.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "JSON request body. Only requires description in body. "
                    + "Status and severity can use default values: UNIMPORTANT and CLOSED, if not specified. Do not give factorydevice or reqistartion in body.",
                    dataType = "string", paramType = "body",
                    example = "{\"description\": \"Sample description\", \"status\": \"open\", \"severity\": \"critical\"}\n"
                            + "You meay leave status and/or severity out of body.")
    })
    @PostMapping("/maintenancetasks/{factoryDeviceId}")
    MaintenanceTask newMaintenanceTask(
            @ApiParam(value = "Given information of the new task. Requires at least description.")
            @RequestBody MaintenanceTask task
            ,@PathVariable Long factoryDeviceId){
        
        if(!factoryDeviceRepository.existsById(factoryDeviceId)){
            throw new FactoryDeviceNotFoundException(factoryDeviceId);
        }
        FactoryDevice device = factoryDeviceRepository.getById(factoryDeviceId);
        task.setDevice(device);
                
        if(task.getDescription() == null){
            throw new DescriptionNotFoundException();   //Description can't be null like severity and status can.
        }
        
        if(task.getSeverity() == null){
            task.setSeverity(DEFAULT_SEVERITY);
        }
        
        if(task.getStatus() == null){
            task.setStatus(DEFAULT_STATUS);
        }
        
        task.setRegistrationDate(LocalDateTime.now());
        
        return maintenanceTaskRepository.save(task);
    }
    
    /**
     * Put-request to modify tasks. Has two paths, one with factorydeviceid if user wants
     * to change task's factorydevice and one without factorydeviceid. This only changes
     * the fields that were given in requestbody by checking if given field is null or not.
     * This can be called without requestbody or facorydeviceid.
     * @param task
     * @param id
     * @param factoryDeviceId
     * @return 
     */
    @ApiOperation(value = "Modify existing task.",
            notes = "Modifies an existing task if given a right id.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "JSON request body. Give only those values you want to change. Registration time can't be changed. "
                    + "Give factorydeviceId in path.",
                    dataType = "string", paramType = "body",
                    example = "{\"description\": \"Sample description\", \"status\": \"open\", \"severity\": \"critical\"}\n"
                            + "You can leave some of these out.")
    })
    @PutMapping({"/maintenancetasks/{id}/{factoryDeviceId}","/maintenancetasks/{id}"})
    MaintenanceTask modifyMaintenanceTaskDevice(@ApiParam(value = "Give the info you want to change.")@RequestBody(required = false) MaintenanceTask task,
            @PathVariable Long id, @PathVariable(required = false) Long factoryDeviceId){
        
        return maintenanceTaskRepository.findById(id).      //Find task or throw error.
                map(maintenancetask -> {
                    
                    if(factoryDeviceId != null){  //Trying to find existing FactoryDevice. If not found-> error.
                        FactoryDevice factoryDevice = factoryDeviceRepository.findById(factoryDeviceId)
                            .orElseThrow(() -> new FactoryDeviceNotFoundException(factoryDeviceId));
                        maintenancetask.setDevice(factoryDevice);
                    }
                    
                    if(task != null){  //Check if body is null and then apply non null fields.
                        
                        if(task.getDescription() != null){
                            maintenancetask.setDescription(task.getDescription());
                        }
                        if(task.getSeverity() != null){
                            maintenancetask.setSeverity(task.getSeverity());
                        }
                        if(task.getStatus() != null){
                            maintenancetask.setStatus(task.getStatus());
                        }
                    }
                    
                    
                    //If you want to update the registration date to match update moment, uncomment the line below.
                    //maintenancetask.setRegistrationDate(LocalDateTime.now());
                    
                    return maintenanceTaskRepository.save(maintenancetask);
                }).orElseThrow(()-> new MaintenanceTaskNotFoundException(id));
    }
    
    
    /**
     * Delete MaintenanceTask if given correct id if id exists.
     * @param id 
     */
    @ApiOperation(value = "Delete mainetnance task with given id.")
    @DeleteMapping("/maintenancetasks/{id}")
    MaintenanceTask deleteMaintenanceTask(@PathVariable Long id){
        if(!maintenanceTaskRepository.existsById(id)){
            throw new MaintenanceTaskNotFoundException(id);
        }
        
        MaintenanceTask task = maintenanceTaskRepository.getById(id); //This is just for return to show what was deleted.
        maintenanceTaskRepository.deleteById(id);
        return task;
    }

}
