package com.springsecurity.config;

import com.springsecurity.model.User;
import com.springsecurity.repository.UserRepository;
import com.springsecurity.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(httpServletRequest);
        boolean valid = tokenService.isValidToken(token);
        if(valid) {
            authenticateUser(token);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void authenticateUser(String token) {
        Long idUser = tokenService.getIdUser(token);
        User user = userRepository.findById(idUser).get();


        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities()));
    }

    private String getToken(HttpServletRequest httpServletRequest) {

        String token =  httpServletRequest.getHeader("Authorization");
        if(!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
