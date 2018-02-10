/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
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
public class StandingBean implements StandingServiceLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistStanding(TournamentEntity tournamentEntity, PersonEntity personEntity) {
        IndividualRoundRobinStandingEntity irrs = new IndividualRoundRobinStandingEntity(personEntity, 0, 0, 0, 0, 0, tournamentEntity);
        em.persist(irrs);
        personEntity.getStandings().add(irrs);
        tournamentEntity.getStandings().add(irrs);
    }
    
}
