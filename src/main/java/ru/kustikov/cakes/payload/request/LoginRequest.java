package ru.kustikov.cakes.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "Instagram cannot be empty")
    private String inst;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
