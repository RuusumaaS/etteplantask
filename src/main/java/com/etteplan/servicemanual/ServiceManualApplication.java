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
import com.etteplan.servicemanual.maintenancetask.MaintenanceTaskRepository;

@SpringBootApplication
public class ServiceManualApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ServiceManualApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final FactoryDeviceRepository repository) {
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
            
        };
    }

}