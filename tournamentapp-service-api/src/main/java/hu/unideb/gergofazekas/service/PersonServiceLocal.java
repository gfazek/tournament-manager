/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.utility.Role;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gfazekas
 */
@Local
public interface PersonServiceLocal {
    
    void persistPerson(PersonEntity personEntity, Role role);
    List<PersonEntity> getPeople();
    void deletePerson(Long id);
    void changeUserStatus(Long id);
    PersonEntity findByUsername(String username);
    void updatePerson(PersonEntity personEntity);
    
}
