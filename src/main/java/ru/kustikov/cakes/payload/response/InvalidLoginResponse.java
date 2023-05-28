package ru.kustikov.cakes.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String inst;
    private String password;

    public InvalidLoginResponse() {
        this.inst = "Invalid instagram";
        this.password = "Invalid password";
    }
}
