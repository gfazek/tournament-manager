/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TournamentBean implements TournamentServiceLocal {

    private static final Logger logger
            = Logger.getLogger("service.TournamentBean");
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistTournament(TournamentEntity tournamentEntity) {
        em.persist(tournamentEntity);
    }

    @Override
    public void persistIndividualCompetitor(IndividualRoundRobinTournamentEntity tournament, PersonEntity personEntity) {
        tournament.getPeople().add(personEntity);
        personEntity.getRoundRobinTournaments().add(tournament);
        em.merge(tournament);
        em.merge(personEntity);
    }

    @Override
    public List<TournamentEntity> getTournaments() {
        return em.createNamedQuery("Tournament.findAll", TournamentEntity.class).getResultList();
    }
    
    @Override
    public List<TournamentEntity> getOpens() {
        return em.createNamedQuery("Tournament.findOpens", TournamentEntity.class).getResultList();
    }

    @Override
    public TournamentEntity findTournament(Long id) {
        logger.log(Level.INFO, "Method findTournament called with id: {0}", id);
        TournamentEntity tournamentEntity = em.find(TournamentEntity.class, id);
        logger.log(Level.INFO, "findTournament tournamEntity's id: {0}", tournamentEntity.getId());
        return tournamentEntity;
    }
    
}
