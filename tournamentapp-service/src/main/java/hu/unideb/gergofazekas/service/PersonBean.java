/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.service.util.PersonPasswordEncoder;
import hu.unideb.gergofazekas.utility.Role;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class PersonBean implements PersonServiceLocal {

    private static final Logger logger = LogManager.getLogger(PersonBean.class);

    @PersistenceContext
    private EntityManager em;

    private static final PersonPasswordEncoder encoder = new PersonPasswordEncoder(new BCryptPasswordEncoder());

    @Override
    public void persistPerson(PersonEntity personEntity, Role role) {
        logger.debug("Persisting person: {}", personEntity);
        personEntity.setPassword(encoder.encode(personEntity.getPassword()));
        RoleEntity roleEntity = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", role).getSingleResult();
        personEntity.getRoles().add(roleEntity);
        if (role == Role.USER) {
            em.persist(personEntity);
        } else if (role == Role.SUPERVISOR) {
            RoleEntity userRole = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", Role.USER).getSingleResult();
            personEntity.getRoles().add(userRole);
            em.persist(personEntity);
            userRole.getPeople().add(personEntity);
        } else if (role == Role.ADMIN) {
            RoleEntity userRole = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", Role.USER).getSingleResult();
            RoleEntity supervisorRole = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", Role.SUPERVISOR).getSingleResult();
            personEntity.getRoles().add(userRole);
            personEntity.getRoles().add(supervisorRole);
            em.persist(personEntity);
            userRole.getPeople().add(personEntity);
            supervisorRole.getPeople().add(personEntity);
        }
        roleEntity.getPeople().add(personEntity);
    }

    @Override
    public List<PersonEntity> getPeople() {
        return em.createNamedQuery("Person.findAll", PersonEntity.class).getResultList();
    }

    @Override
    public void deletePerson(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        logger.debug("Deleting person: {}", personEntity);
        em.remove(personEntity);
    }

    @Override
    public void changeUserStatus(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        logger.debug("Changing Person's status: {}", personEntity);
        personEntity.setEnabled(!personEntity.isEnabled());
//        em.merge(personEntity);
    }

    @Override
    public PersonEntity findByUsername(String username) {
        return em.createNamedQuery("Person.findByUsername", PersonEntity.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public void updatePerson(PersonEntity personEntity) {
        personEntity.setPassword(encoder.encode(personEntity.getPassword()));
        logger.debug("Updating person: {}", personEntity);
        em.merge(personEntity);
    }

    @Override
    public void makeSupervisor(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        RoleEntity supervisorRole = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", Role.SUPERVISOR).getSingleResult();
        personEntity.getRoles().add(supervisorRole);
        supervisorRole.getPeople().add(personEntity);
    }

    @Override
    public void makeUser(Long id) {
        PersonEntity personEntity = em.find(PersonEntity.class, id);
        RoleEntity supervisorRole = em.createNamedQuery("Role.findByName", RoleEntity.class).setParameter("rolename", Role.SUPERVISOR).getSingleResult();
        personEntity.getRoles().remove(supervisorRole);
        supervisorRole.getPeople().remove(personEntity);
    }

}
