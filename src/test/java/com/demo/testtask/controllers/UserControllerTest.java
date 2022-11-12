package com.demo.testtask.controllers;

import com.demo.testtask.dto.UserResponseDTO;
import com.demo.testtask.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void testController_whenGetById_thenStatus200andPersonReturned() throws Exception {
        UserResponseDTO user = new UserResponseDTO(1L, "Vasya", "Pupkin", 31);

        Mockito.when(service.findUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/getUser")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Vasya"))
                .andExpect(jsonPath("$.lastName").value("Pupkin"))
                .andExpect(jsonPath("$.age").value("31"));
    }

    @Test
    public void testController_whenGetById_thenStatus404NotFound() throws Exception {
        Mockito.when(service.findUserById(5L)).thenThrow(new NullPointerException());

        mockMvc.perform(get("/getUser")
                        .param("id", String.valueOf(5L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testController_whenGetById_thenStatus400BadRequest() throws Exception {
        Mockito.when(service.findUserById(-1L)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/getUser")
                        .param("id", String.valueOf(-1L))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}
