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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class TournamentBean implements TournamentServiceLocal {

       private static final Logger logger = LogManager.getLogger(InitializerBean.class);
    
    @EJB
    private PersonServiceLocal personServiceLocal;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistTournament(TournamentEntity tournamentEntity) {
        em.persist(tournamentEntity);
    }

    @Override
    public void persistIndividualCompetitor(IndividualRoundRobinTournamentEntity tournament, PersonEntity personEntity) {
        logger.debug("Persisting individual competitor: tournament: {} | person: {}", tournament, personEntity);
        tournament.getPeople().add(personEntity);
        personEntity.getRoundRobinTournaments().add(tournament);
//        em.merge(tournament);
//        em.merge(personEntity);
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
        TournamentEntity tournamentEntity = em.find(TournamentEntity.class, id);
        return tournamentEntity;
    }

    @Override
    public void persistEntry(Long tournamentId, String username) {
        logger.debug("Persisting entry: tournamentid: {} | usernsme: {}", tournamentId, username);
        TournamentEntity tournamentEntity = findTournament(tournamentId);
        PersonEntity personEntity = personServiceLocal.findByUsername(username);
        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            tmp.getPeople().add(personEntity);
            personEntity.getRoundRobinTournaments().add((IndividualRoundRobinTournamentEntity)tournamentEntity);
        }
//        em.merge(tournamentEntity);
//        em.merge(personEntity);
    }

    @Override
    public List<PersonEntity> getIndividualCompetitors(Long id) {
        List<PersonEntity> competitors = em.createNamedQuery("IndividualRoundRobinTournament.findCompetitors", PersonEntity.class).setParameter("id", id).getResultList();
        logger.debug("Individual Competitors: tournamentid: {} | competitors: {}", id, competitors);
        return competitors;
    }
    
    
    
}
