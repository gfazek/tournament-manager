/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.Role;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class PersonBean implements PersonServiceLocal{

    private static final Logger logger
            = Logger.getLogger("service.PersonBean");
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistPerson(PersonEntity person, RoleEntity role) {
        role.getPeople().add(person);
        person.getRoles().add(role);
        em.merge(role);
        em.persist(person);
    }

    @Override
    public void persistPerson(PersonEntity personEntity, Role role) {
        logger.info("personentity is: " + personEntity);
        logger.info("In persistPerson....");
        logger.info("Role is: " + role.name());
        //RoleEntity roleEntity = (RoleEntity) em.createNamedQuery("findRoleByName").getSingleResult();
        RoleEntity roleEntity = em.find(RoleEntity.class, new Long(1));
        logger.info("roleentity is: " + roleEntity);
        roleEntity.getPeople().add(personEntity);
        personEntity.getRoles().add(roleEntity);
        em.merge(roleEntity);
        em.persist(personEntity);
    }
    
    @Override
    public List<PersonEntity> getPeople() {
        return em.createNamedQuery("Person.findAll", PersonEntity.class).getResultList();
    }

    @Override
    public void deletePerson(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        logger.log(Level.INFO, ">>>>>>>>>>" + personEntity.toString());
        em.remove(personEntity);
    }

    @Override
    public void changeUserStatus(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        personEntity.setEnabled(!personEntity.isEnabled());
        em.merge(personEntity);
    }
    
    
}
