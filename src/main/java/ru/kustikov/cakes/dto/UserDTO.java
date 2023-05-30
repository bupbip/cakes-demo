package ru.kustikov.cakes.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String instagram;
    @NotEmpty
    private String name;
}
