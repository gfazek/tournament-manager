/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.vo.GenderVo;
import hu.unideb.gergofazekas.vo.PersonVo;
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

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public PersonVo createPerson(PersonVo personVo) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setUsername(personVo.getUsername());
        personEntity.setEmail(personVo.getEmail());
        personEntity.setPassword(personVo.getPassword());
        personEntity.setFirstName(personVo.getFirstName());
        personEntity.setLastName(personVo.getLastName());
        personEntity.setGender(Gender.valueOf(personVo.getGender().name()));
        personEntity.setDob(personVo.getDob());
        em.persist(personEntity);
        return personVo;
    }

    @Override
    public PersonVo getPersonByUsername(String username) {
        PersonEntity personEntity = (PersonEntity) em.createNamedQuery("findPersonByUsername").setParameter("username", username).getSingleResult();
        PersonVo personVo = new PersonVo();
        personVo.setUsername(personEntity.getUsername());
        personVo.setEmail(personEntity.getEmail());
        personVo.setPassword(personEntity.getPassword());
        personVo.setEnabled(personEntity.isEnabled());
        personVo.setFirstName(personEntity.getFirstName());
        personVo.setLastName(personEntity.getLastName());
        personVo.setGender(GenderVo.valueOf(personEntity.getGender().name()));
        personVo.setDob(personEntity.getDob());
        return personVo;
    }
    
    @Override
    public void persistPerson(PersonEntity person, RoleEntity role) {
        role.getPeople().add(person);
        person.getRoles().add(role);
        em.merge(role);
        em.persist(person);
    }
    
}
