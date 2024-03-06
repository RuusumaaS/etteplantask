package com.etteplan.servicemanual.factorydevice;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class FactoryDeviceController {

    private final FactoryDeviceRepository factoryDeviceRepository;
    private final MaintenanceTaskRepository maintenanceTaskRepository;

    FactoryDeviceController(FactoryDeviceRepository factoryDeviceRepository,MaintenanceTaskRepository maintenanceTaskRepository) {
        this.factoryDeviceRepository = factoryDeviceRepository;
        this.maintenanceTaskRepository = maintenanceTaskRepository;
    }

    @GetMapping("/factorydevices")
    List<FactoryDevice> all() {
        return factoryDeviceRepository.findAll();
    }

    @GetMapping("/factorydevices/{id}")
    FactoryDevice one(@PathVariable Long id) {
        return factoryDeviceRepository.findById(id)
            .orElseThrow(() -> new FactoryDeviceNotFoundException(id));
    }
    
    @PostMapping("/maintenansetasks")
    MaintenanceTask newTask(@RequestBody MaintenanceTask task){
        return maintenanceTaskRepository.save(task);
    }
    
    @DeleteMapping("/maintenancetasks/{id}")
    void deleteMaintenanceTask(@PathVariable Long id){
        maintenanceTaskRepository.deleteById(id);
    }
}