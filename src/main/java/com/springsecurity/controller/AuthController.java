package com.springsecurity.controller;


import com.springsecurity.dto.LoginDTO;
import com.springsecurity.dto.TokenDTO;
import com.springsecurity.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO body) {
        UsernamePasswordAuthenticationToken data = body.convert();
        try {
            Authentication authentication = authenticationManager.authenticate(data);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


    }
}
