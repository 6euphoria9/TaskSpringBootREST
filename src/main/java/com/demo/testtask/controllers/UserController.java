package com.demo.testtask.controllers;

import com.demo.testtask.dto.UserResponseDTO;
import com.demo.testtask.exceptions.BadArgumentException;
import com.demo.testtask.exceptions.NotFoundException;
import com.demo.testtask.services.UserService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<UserResponseDTO> findUserById(@NotNull @RequestParam Long id) {
        try {
            return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
        } catch (BadArgumentException bae) {
            throw new BadArgumentException();
        } catch (NotFoundException nfe) {
            throw new NotFoundException();
        }
    }

}
