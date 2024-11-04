package com.spring.security.service.implementation;

import com.nimbusds.jose.JOSEException;
import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.persistence.repository.UserRepository;
import com.spring.security.service.interfaces.AuthService;
import com.spring.security.service.interfaces.JwtUtilService;
import com.spring.security.service.model.dto.LoginDTO;
import com.spring.security.service.model.dto.ResponseDTO;
import com.spring.security.service.model.validation.UserValidation;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtilService jwtUtilService;
    
    @Autowired
    private UserValidation userValidation;
    
    @Override
    public Map<String, String> login(LoginDTO login) throws Exception {
        Map<String, String> jwt = new HashMap<>();
        
        try {
            Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());
            
            if(user.isEmpty()) {
                jwt.put("error", "user not registered");
                return jwt;
            }
            
            if(verifyPassword(login.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", jwtUtilService.generateJwt(user.get().getId()));
            } else {
                jwt.put("error", "authentication failed");
            }
        } catch(JOSEException | IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace(System.out);
        }
        
        return jwt;
    }
    
    @Override
    public ResponseDTO register(UserEntity user) throws Exception {
        ResponseDTO response = userValidation.validate(user);
        try {
            if(response.getNumberOfErrors() > 0) {
                return response;
            }
            
            List<UserEntity> users = userRepository.findAll();
            
            for(UserEntity userRepeat : users) {
                if(userRepeat != null) {
                    response.setNumberOfErrors(1);
                    response.setMessage("user already exists");
                    return response;
                }
            }
            
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            response.setMessage("user created successfully");
            
        } catch(Exception ex) {
            ex.printStackTrace(System.out);
        }
        
        return response;
    }
    
    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(storedPassword, enteredPassword);
    }
}
