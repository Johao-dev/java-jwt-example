package com.spring.security.configuration.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.spring.security.service.interfaces.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtilService jwtUtilService;

    JWTAuthorizationFilter(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        String header = request.getHeader("authorization");
        
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = header.substring(7);
        
        try {
            JWTClaimsSet claims = jwtUtilService.parseJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), 
                            null,
                            Collections.emptyList());
            
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);
            
        } catch (NoSuchAlgorithmException |
                InvalidKeySpecException |
                ParseException |
                JOSEException ex) {
            ex.printStackTrace(System.out);
        }
        
        filterChain.doFilter(request, response);
    }
}
