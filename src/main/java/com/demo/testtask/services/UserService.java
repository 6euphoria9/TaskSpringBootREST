package com.demo.testtask.services;

import com.demo.testtask.dto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserService {

    UserResponseDTO findUserById(Long id);

}
