/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.service.PersonServiceLocal;
import java.util.List;
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
public class UsersBean {

    private List<PersonEntity> users;
    
    @EJB
    private PersonServiceLocal personServiceLocal;
    
    public UsersBean() {
    }
    
    @PostConstruct
    public void init() {
        users = personServiceLocal.getPeople();
    }

    public List<PersonEntity> getUsers() {
        return users;
    }

    public void setUsers(List<PersonEntity> users) {
        this.users = users;
    }

    public PersonServiceLocal getPersonServiceLocal() {
        return personServiceLocal;
    }

    public void setPersonServiceLocal(PersonServiceLocal personServiceLocal) {
        this.personServiceLocal = personServiceLocal;
    }
    
}
