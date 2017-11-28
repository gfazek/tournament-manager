/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.MatchEntity;
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
    public void persistMatch(MatchEntity matchEntity, TournamentEntity tournamentEntity) {
        tournamentEntity.getMatches().add(matchEntity);
        matchEntity.setTournament(tournamentEntity);
        em.merge(tournamentEntity);
        em.persist(matchEntity);
    }
    
}
