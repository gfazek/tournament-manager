/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.service.PersonServiceLocal;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.Role;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class ProfileBean {
    
    private static final Logger logger = LogManager.getLogger(ProfileBean.class);
    
    private PersonEntity personEntity;
    private Gender[] genders;
    
    @EJB
    private PersonServiceLocal personServiceLocal;

    public ProfileBean() {
    }
    
    @PostConstruct
    public void init() {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        personEntity = personServiceLocal.findByUsername(username);
    }
    
    public String updateProfile() {
        personServiceLocal.updatePerson(personEntity);
        return "profile";
    }
    
    public Gender[] getGenders() {
        return Gender.values();
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }
    
}
