/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.etteplan.servicemanual.maintenancetask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MaintenanceTaskControllerTest {
    
    @Autowired
    private MockMvc mvc;
    

    @Test
    public void getMaintenanceTasks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    public void getMaintenanceTaskNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/-1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void getFactoryDeviceNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/factorydevice/-1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
  
    @Test
    public void getByStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/status/open").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    
   @Test
    public void getIllegalStatus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/status/locked").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void getBySeverity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/severity/critical").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    
    @Test
    public void getIllegalSeverity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/maintenancetasks/severity/uncritical").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
    
    
    @Test
    public void addTaskFailFactoryId() throws Exception {
        
            String requestBody = "{\"description\": \"Short descritpion\"}";
            MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/maintenancetasks/-11").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn();

            String responseBody = result.getResponse().getContentAsString();
    
            assertThat(responseBody).isEqualTo("Could not find factory device -11");
        
    }
    
    @Test
    public void addTaskFailDescription() throws Exception {
        String requestBody = "{\"severity\": \"critical\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/maintenancetasks/1").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Maintenancetask needs a description.");
    }
    
    @Test
    public void addTaskFailSeverity() throws Exception {
        String requestBody = "{\"severity\": \"closed\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/maintenancetasks/1").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Severity must be uniportant, important or critical. All in lowercase."
                + "Given severity was closed");
    }
    @Test
    public void addTaskFailStatus() throws Exception {
        String requestBody = "{\"status\": \"close\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/maintenancetasks/1").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();

        assertThat(responseBody).isEqualTo("Status has to be either open or closed. Given status was close");
    }
    
    @Test
    public void testDeleteMaintenanceTaskNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/maintenancetasks/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    
}
