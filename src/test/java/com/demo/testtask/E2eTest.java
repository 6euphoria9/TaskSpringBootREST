package com.demo.testtask;


import com.demo.testtask.domain.User;
import com.demo.testtask.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Period;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class E2eTest {

    public static final int ID = 1;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = repository.findById(1L).get();
    }

    @Test
    public void testGetUserById_whenGetMethod_thenStatus200() throws Exception {
        String actual = mvc.perform(get("/getUser")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Integer age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
        String expected = String.format("{\"id\":1,\"firstName\":\"Vasya\",\"lastName\":\"Pupkin\",\"age\":%d}", age);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testGetUserById() throws Exception {
        mvc.perform(get("/getUser")
                        .param("id", String.valueOf(ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()));
    }

    @Test
    public void testGetUserById_withNullParameter_thenStatus400() throws Exception {
        mvc.perform(get("/getUser")
                        .param("id", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetUserById_withBadRequest_thenStatus400() throws Exception {
        mvc.perform(get("/getUser")
                        .param("id", "adqw")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetUserById_withNotFound_thenStatus404() throws Exception {
        mvc.perform(get("/getUser")
                        .param("id", String.valueOf(400))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetUserById_withNotNullParameterAndExistedUser_thenStatus200() throws Exception {
        mvc.perform(get("/getUser")
                        .param("id", String.valueOf(1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}
