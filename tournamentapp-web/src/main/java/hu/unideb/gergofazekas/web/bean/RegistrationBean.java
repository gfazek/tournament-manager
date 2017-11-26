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
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class RegistrationBean {
    
    private static final Logger logger
            = Logger.getLogger("service.InitializerBean");
    
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
    
    public void createPerson() {
        logger.info("In createPerson");
        personServiceLocal.persistPerson(personEntity, Role.USER);
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
