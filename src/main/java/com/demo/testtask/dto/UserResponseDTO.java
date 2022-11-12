package com.demo.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

}
