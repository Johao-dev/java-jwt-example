package com.spring.security.service.implementation;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.persistence.repository.UserRepository;
import com.spring.security.service.interfaces.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
