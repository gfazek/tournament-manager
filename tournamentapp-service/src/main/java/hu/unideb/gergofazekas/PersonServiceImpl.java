/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gfazekas
 */
@Local(PersonService.class)
@Stateless
public class PersonServiceImpl implements PersonService {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addPerson(PersonVO personVO) {
         Person p = new Person();
         p.setName(personVO.getName());
         em.persist(p);
    }
    
}
