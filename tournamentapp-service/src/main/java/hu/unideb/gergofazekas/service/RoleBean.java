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
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class RoleBean implements RoleServiceLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistRole(RoleEntity roleEntity) {
        em.persist(roleEntity);
    }
    
    @Override
    public void addPeopleToRole(RoleEntity role, List<PersonEntity> people) {
        role.getPeople().addAll(people);
        for (PersonEntity person : people) {
            person.getRoles().add(role);
        }
        em.merge(role);
    }
    
}
