/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author gfazekas
 */
public class PersonPasswordEncoder {
    
    private final PasswordEncoder passwordEncoder;
            
    public PersonPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public String encode(String pwd) {
        return passwordEncoder.encode(pwd);
    }
    
}
