/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
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
public class MatchBean implements MatchServiceLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistMatch(IndividualMatchEntity individualMatchEntity, TournamentEntity tournamentEntity) {
        individualMatchEntity.setTournament(tournamentEntity);
        em.persist(individualMatchEntity);
        PersonEntity homePerson = individualMatchEntity.getHomeCompetitor();
        PersonEntity awayPerson = individualMatchEntity.getAwayCompetitor();
        tournamentEntity.getMatches().add(individualMatchEntity);
        homePerson.getHomeMatches().add(individualMatchEntity);
        awayPerson.getAwayMatches().add(individualMatchEntity);
    }
    
    
    
}
