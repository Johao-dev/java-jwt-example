package com.spring.security.controller;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.service.interfaces.AuthService;
import com.spring.security.service.model.dto.LoginDTO;
import com.spring.security.service.model.dto.ResponseDTO;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    private ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginReques) throws Exception {
        Map<String, String> login = authService.login(loginReques);
        
        if(login.containsKey("jwt"))
            return new ResponseEntity<>(login, HttpStatus.OK);
        
        return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
    }
}
