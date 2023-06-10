package ru.kustikov.cakes.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString(of = {"id", "name", "instagram", "phone"})
public class UserDTO {
    private Long id;
    private String instagram;
    private String name;
    private String phone;
}
