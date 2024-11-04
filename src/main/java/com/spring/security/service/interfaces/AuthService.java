package com.spring.security.service.interfaces;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.service.model.dto.LoginDTO;
import com.spring.security.service.model.dto.ResponseDTO;
import java.util.Map;

public interface AuthService {
    
    public Map<String, String> login(LoginDTO login) throws Exception;
    
    public ResponseDTO register(UserEntity user) throws Exception;
}
