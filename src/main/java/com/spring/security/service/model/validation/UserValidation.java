package com.spring.security.service.model.validation;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.service.model.dto.ResponseDTO;

public class UserValidation {
    
    public ResponseDTO validate(UserEntity user) {
        ResponseDTO response = new ResponseDTO();
        
        response.setNumberOfErrors(0);
        
        if(user.getFirstName() == null ||
                user.getFirstName().length() < 3 ||
                user.getFirstName().length() > 15) {
            
            response.setNumberOfErrors(response.getNumberOfErrors() + 1);
            response.setMessage(
                    "The first name field shouldn't be null "
                            + "and also shouldn't less than 3 and "
                            + "greater than 15.");
        }
        
        if(user.getLastName() == null ||
                user.getLastName().length() < 3 ||
                user.getLastName().length() > 30) {
            
            response.setNumberOfErrors(response.getNumberOfErrors() + 1);
            response.setMessage(
                    "The last name field shouldn't be null "
                            + "and also shouldn't less than 3 and "
                            + "greater than 30.");
        }
        
        if(user.getEmail() == null ||
                !user.getEmail()
                        .matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            
            response.setNumberOfErrors(response.getNumberOfErrors() + 1);
            response.setMessage(
                    "The email field is not valid.");
        }
        
        if(user.getPassword() == null ||
                !user.getPassword()
                        .matches("^(?=.\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$")) {
            
            response.setNumberOfErrors(response.getNumberOfErrors() + 1);
            response.setMessage(
                    "The password must be between 8 and 16 characters,"
                            + "at least one number, one uppercase letter,"
                            + "and one lowercase letter.");
        }
        
        return response;
    }
}
