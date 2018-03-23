/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.service.PersonServiceLocal;
import hu.unideb.gergofazekas.utility.Role;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */

@Named
@ViewScoped
public class UsersBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(UsersBean.class);
    
    private List<PersonEntity> users;
    private List<PersonEntity> filteredUsers;
    
    @EJB
    private PersonServiceLocal personServiceLocal;
    
    public UsersBean() {
    }
    
    @PostConstruct
    public void init() {
        users = personServiceLocal.getPeople();
    }
    
    public boolean isSupervisor(PersonEntity personEntity) {
        for (RoleEntity role : personEntity.getRoles()) {
            if (role.getRole() == Role.SUPERVISOR) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isAdmin(PersonEntity personEntity) {
        for (RoleEntity role : personEntity.getRoles()) {
            if (role.getRole() == Role.ADMIN) {
                return true;
            }
        }
        return false;
    }
    
    public void makeSupervisor(Long id) {
        personServiceLocal.makeSupervisor(id);
        init();
    }
    
    public void makeUser(Long id) {
        personServiceLocal.makeUser(id);
        init();
    }
    
    public void deleteUser(Long id) {
        personServiceLocal.deletePerson(id);
        init();
    }
    
    public void changeUserStatus(Long id) {
        personServiceLocal.changeUserStatus(id);
        init();
    }

    public List<PersonEntity> getUsers() {
        return users;
    }

    public void setUsers(List<PersonEntity> users) {
        this.users = users;
    }

    public List<PersonEntity> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<PersonEntity> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public PersonServiceLocal getPersonServiceLocal() {
        return personServiceLocal;
    }

    public void setPersonServiceLocal(PersonServiceLocal personServiceLocal) {
        this.personServiceLocal = personServiceLocal;
    }
    
}
