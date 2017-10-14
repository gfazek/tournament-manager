/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web;

import hu.unideb.gergofazekas.service.PersonServiceLocal;
import hu.unideb.gergofazekas.vo.GenderVo;
import hu.unideb.gergofazekas.vo.PersonVo;
import java.util.Date;
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
    
    private PersonVo personVo;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private GenderVo gender;
    private Date dob;
    
    @EJB
    private PersonServiceLocal personServiceLocal;

    public RegistrationBean() {
    }
    
    public void createPerson() {
        personServiceLocal.createPerson(personVo);
    }

    public PersonVo getPersonVo() {
        return personVo;
    }

    public void setPersonVo(PersonVo personVo) {
        this.personVo = personVo;
    }
    
}
