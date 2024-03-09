package com.etteplan.servicemanual;

import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.etteplan.servicemanual.factorydevice.FactoryDevice;
import com.etteplan.servicemanual.factorydevice.FactoryDeviceRepository;
import com.etteplan.servicemanual.maintenancetask.MaintenanceTask;
import com.etteplan.servicemanual.maintenancetask.MaintenanceTaskRepository;
import com.etteplan.servicemanual.maintenancetask.Severity;
import com.etteplan.servicemanual.maintenancetask.Status;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@Validated 
public class ServiceManualApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ServiceManualApplication.class, args);
    }

    /**
     * This CommnandLineRunner fills the database. It uses the seeddata you gave me to fill facotryDeviceRepository.
     * After those are saved, it saves 10 tasks that I randomly created in sometasksforstart.csv file. 
     * This function is meant simply so that you don't have to start from nothing when trying my API.
     * I you don't want any data either remove this or comment it. It will only fill the databse first time you run it.
     * @param repository
     * @param taskRepository
     * @return 
     */
    @Bean
    public CommandLineRunner commandLineRunner(final FactoryDeviceRepository repository,final MaintenanceTaskRepository taskRepository) {
        return (args) -> {

            //In case the database is empty, it will be filled with seeddata.csv-files content.
            if(repository.count() == 0){
                try (BufferedReader br = new BufferedReader(new FileReader("seeddata.csv"))) {
                    br.readLine();
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        String name = values[0];
                        int year = Integer.parseInt(values[1]);
                        String type = values[2];
                        FactoryDevice device = new FactoryDevice(name,year,type);
                        repository.save(device);
                    }
                }
                catch(Exception e){

                }
            }
            if(taskRepository.count() == 0){
                try (BufferedReader br = new BufferedReader(new FileReader("sometasksforstart.csv"))) {
                    br.readLine();
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        Long factoryDeviceId = Long.parseLong(values[0]);
                        FactoryDevice device = repository.getById(factoryDeviceId);
                        String description = values[1];
                        LocalDateTime registrationTime = LocalDateTime.parse(values[2]);
                        Severity severity = Severity.valueOf(values[3].toUpperCase());
                        Status status = Status.valueOf(values[4].toUpperCase());
                        MaintenanceTask task = new MaintenanceTask(device,description,registrationTime,severity,status);
                        taskRepository.save(task);
                    }
                }
                catch(Exception e){

                }
            }
            
        };
    }

}