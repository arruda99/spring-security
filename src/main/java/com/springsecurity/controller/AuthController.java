package com.springsecurity.controller;


import com.springsecurity.dto.LoginDTO;
import com.springsecurity.dto.TokenDTO;
import com.springsecurity.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<TokenDTO> authenticate(LoginDTO body) {
        UsernamePasswordAuthenticationToken data = body.convert();
        Authentication authentication = authenticationManager.authenticate(data);
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
    }
}
