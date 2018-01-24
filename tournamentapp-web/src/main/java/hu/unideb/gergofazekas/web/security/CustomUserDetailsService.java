/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.security;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.service.PersonServiceLocal;
import hu.unideb.gergofazekas.utility.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author gfazekas
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private static final Logger logger
            = Logger.getLogger("service.PersonBean");
    
    @EJB(mappedName = "java:global/Tournament_Manager_Application_EAR_Module/tournamentapp-service-1.0-SNAPSHOT/PersonBean")
    private PersonServiceLocal personServiceLocal;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.log(Level.INFO, "PersonServiceLocal is: {0}", personServiceLocal);
        PersonEntity person = personServiceLocal.findByUsername(username);
        
        if (person == null) {
            throw  new UsernameNotFoundException("User details not found with this username: " + username);
        }
        List<SimpleGrantedAuthority> authorities = getAuthorities(person.getRoles());
        User user = new User(person.getUsername(), person.getPassword(), person.isEnabled(), true, true, true, authorities);
        return user;
    }
    
    private List<SimpleGrantedAuthority> getAuthorities(List<RoleEntity> roles) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (RoleEntity role : roles) {
            if (role.getRole() == Role.USER) {
                auths.add(new SimpleGrantedAuthority("ROLE_USER"));
            } 
            if (role.getRole() == Role.ADMIN) {
                auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        return auths;
    }

    public void setPersonServiceLocal(PersonServiceLocal personServiceLocal) {
        this.personServiceLocal = personServiceLocal;
    }
    
}