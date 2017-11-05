/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.utility.Role;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gfazekas
 */
@Startup
@Singleton
public class InitializerBean {
    
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        persistRole(Role.USER);
        persistRole(Role.ADMIN);
    }
    
    private void persistRole(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        em.persist(roleEntity);
    }
}
