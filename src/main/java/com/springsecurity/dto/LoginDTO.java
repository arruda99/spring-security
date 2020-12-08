package com.springsecurity.dto;

import lombok.Data;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class LoginDTO {
    @NonNull
    private String user;
    @NonNull
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(this.user,this.password);
    }
}
