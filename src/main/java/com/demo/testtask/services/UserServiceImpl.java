package com.demo.testtask.services;

import com.demo.testtask.domain.User;
import com.demo.testtask.dto.UserResponseDTO;
import com.demo.testtask.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO findUserById(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NullPointerException();
        }

        User foundUser = user.get();

        return new UserResponseDTO()
                .setId(foundUser.getId())
                .setFirstName(foundUser.getFirstName())
                .setLastName(foundUser.getLastName())
                .setAge(Period.between(foundUser.getDateOfBirth(), LocalDate.now()).getYears());
    }
}
