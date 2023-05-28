package ru.kustikov.cakes.payload.request;

import lombok.Data;
import ru.kustikov.cakes.annotations.PasswordMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {
    @NotBlank(message = "Instagram is required")
    private String inst;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String confirmPassword;
}
