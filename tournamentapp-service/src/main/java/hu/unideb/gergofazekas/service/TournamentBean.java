/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.utility.TournamentStatus;
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

    private static final Logger logger = LogManager.getLogger(TournamentBean.class);

    @EJB
    private PersonServiceLocal personServiceLocal;

    @EJB
    private MatchServiceLocal matchServiceLocal;

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
        logger.debug("TournamentEntity: {}", tournamentEntity);
        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
            IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            logger.debug("IndividualRoundRobinTournamentEntity: {}", irrt);
            irrt.getPeople().size();
        }
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
            personEntity.getRoundRobinTournaments().add((IndividualRoundRobinTournamentEntity) tournamentEntity);
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

    @Override
    public void kickoff(Long id) {
        TournamentEntity tournamentEntity = findTournament(id);
        logger.debug("Kicking off: {}", tournamentEntity);
        tournamentEntity.setStatus(TournamentStatus.IN_PROGRESS);
        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
            IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            List<PersonEntity> competitors = irrt.getPeople();
            logger.debug("Instantiating matches for competitors: {}", competitors);
            for (PersonEntity p1 : competitors) {
                logger.debug("p1: {}", p1);
                List<PersonEntity> tmp = competitors.subList(competitors.indexOf(p1) + 1, competitors.size());
                logger.debug("tmp: {}", tmp);
                for (PersonEntity p2 : tmp) {
                    logger.debug("p2: {}", p2);
                    matchServiceLocal.persistMatch(new IndividualMatchEntity(p1, p2), tournamentEntity);
                }
            }
        }
    }

}
