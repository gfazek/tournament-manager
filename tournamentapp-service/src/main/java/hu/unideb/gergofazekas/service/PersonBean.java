/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.vo.GenderVo;
import hu.unideb.gergofazekas.vo.PersonVo;
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
    
}
