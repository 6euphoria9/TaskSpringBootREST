package com.demo.testtask.services;

import com.demo.testtask.domain.User;
import com.demo.testtask.dto.UserResponseDTO;
import com.demo.testtask.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    private UserResponseDTO userResponseDTO;

    private User user;

    @Before
    public void setUp() throws Exception {
        userResponseDTO = new UserResponseDTO()
                .setId(1L)
                .setFirstName("Vasya")
                .setLastName("Pupkin")
                .setAge(31);

        user = new User()
                .setId(1L)
                .setFirstName("Vasya")
                .setLastName("Pupkin")
                .setDateOfBirth(LocalDate.of(1990, 12, 12));
    }


    @Test
    public void testService_whenFindUserById_thenReturnUserDTOObject() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO savedUserDTO = service.findUserById(user.getId());

        assertThat(savedUserDTO).isEqualTo(userResponseDTO);
    }

    @Test
    public void testService_whenFindUserById_thenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.findUserById(-5L));
    }

    @Test
    public void testService_whenFindUserById_thenThrowNullPointerException() {
        when(repository.findById(10L)).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () ->
                service.findUserById(10L));
    }


}