package com.spring.security.service.interfaces;

import com.spring.security.persistence.entity.UserEntity;
import java.util.List;

public interface UserService {
    
    public List<UserEntity> findAllUsers();
}
