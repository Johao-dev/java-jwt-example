package com.spring.security.service.interfaces;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface JwtUtilService {
    
    public String generateJwt(Long id)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeySpecException, JOSEException;
    
    public JWTClaimsSet parseJWT(String token)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
            ParseException, JOSEException;
}
