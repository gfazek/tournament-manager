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

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class RegistrationBean {
    
    private static final Logger logger = LogManager.getLogger(RegistrationBean.class);
    
    private PersonEntity personEntity;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Gender[] genders;
    private Date dob;
    
    @EJB
    private PersonServiceLocal personServiceLocal;

    public RegistrationBean() {
    }
    
    @PostConstruct
    public void init() {
        personEntity = new PersonEntity();
    }
    
    public String createPerson() {
        personServiceLocal.persistPerson(personEntity, Role.USER);
        return "index";
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
