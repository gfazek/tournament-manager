/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualEliminationMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.StandingEntity;
import java.util.List;
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
public class StandingBean implements StandingServiceLocal {

    private static final Logger logger = LogManager.getLogger(StandingBean.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persistStanding(IndividualRoundRobinStandingEntity standingEntity) {
        em.persist(standingEntity);
        standingEntity.getPersonEntity().getStandings().add(standingEntity);
        standingEntity.getTournamentEntity().getStandings().add(standingEntity);
    }

    @Override
    public IndividualRoundRobinStandingEntity findIndividualRoundRobinOne(Long pid, Long tid) {
        return em.createNamedQuery("Standing.findIndividualRoundRobinOne", IndividualRoundRobinStandingEntity.class).setParameter("pid", pid).setParameter("tid", tid).getSingleResult();
    }

    @Override
    public IndividualEliminationStandingEntity findIndividualEliminationOne(Long pid, Long tid) {
        return em.createNamedQuery("Standing.findIndividualEliminationOne", IndividualEliminationStandingEntity.class).setParameter("pid", pid).setParameter("tid", tid).getSingleResult();
    }

    @Override
    public void persistStanding(IndividualRoundRobinTournamentEntity tournament, PersonEntity person) {
        IndividualRoundRobinStandingEntity irrs = new IndividualRoundRobinStandingEntity(person, 0, 0, 0, 0, 0, tournament);
        logger.debug("Persisting Standing: {}", irrs);
        em.persist(irrs);
        tournament.getStandings().add(irrs);
        person.getStandings().add(irrs);
    }

    @Override
    public void updateStandings(IndividualMatchEntity individualMatchEntity) {
        IndividualRoundRobinStandingEntity homeStanding = (IndividualRoundRobinStandingEntity) findIndividualRoundRobinOne(individualMatchEntity.getHomeCompetitor().getId(), individualMatchEntity.getTournament().getId());
        IndividualRoundRobinStandingEntity awayStanding = (IndividualRoundRobinStandingEntity) findIndividualRoundRobinOne(individualMatchEntity.getAwayCompetitor().getId(), individualMatchEntity.getTournament().getId());
        IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) individualMatchEntity.getTournament();
        homeStanding.setPlayed(homeStanding.getPlayed() + 1);
        awayStanding.setPlayed(awayStanding.getPlayed() + 1);

        if (individualMatchEntity.getHomeScore() > individualMatchEntity.getAwayScore()) {
            homeStanding.setWon(homeStanding.getWon() + 1);
            homeStanding.setPoints(homeStanding.getPoints() + irrt.getWinPoint());
            awayStanding.setPoints(awayStanding.getPoints() + irrt.getLoosePoint());
            awayStanding.setLost(awayStanding.getLost() + 1);
        } else if (individualMatchEntity.getHomeScore() < individualMatchEntity.getAwayScore()) {
            awayStanding.setWon(awayStanding.getWon() + 1);
            awayStanding.setPoints(awayStanding.getPoints() + irrt.getWinPoint());
            homeStanding.setPoints(homeStanding.getPoints() + irrt.getLoosePoint());
            homeStanding.setLost(homeStanding.getLost() + 1);
        } else {
            homeStanding.setPoints(homeStanding.getPoints() + irrt.getDrawPoint());
            awayStanding.setPoints(awayStanding.getPoints() + irrt.getDrawPoint());
            homeStanding.setDrawn(homeStanding.getDrawn() + 1);
            awayStanding.setDrawn(awayStanding.getDrawn() + 1);
        }

    }

    @Override
    public void updateStandings(IndividualEliminationMatchEntity match) {
        IndividualEliminationStandingEntity standing;
//        IndividualEliminationTournamentEntity tournament;
        if (match.getHomeScore() > match.getAwayScore()) {
            standing = findIndividualEliminationOne(match.getHomeCompetitor().getId(), match.getTournament().getId());
        } else {
            standing = findIndividualEliminationOne(match.getAwayCompetitor().getId(), match.getTournament().getId());
        }
        logger.debug("Update individual elimination standing: {}", standing);
        standing.setRound(standing.getRound() + 1);
//        tournament = (IndividualEliminationTournamentEntity) match.getTournament();
//        Long rounds = tournament.getNumberOfRounds();
    }

    @Override
    public List<StandingEntity> findByTournament(Long tournamentId) {
        return em.createNamedQuery("Standing.findByTournament", StandingEntity.class).setParameter("id", tournamentId).getResultList();
    }

    @Override
    public List<IndividualEliminationStandingEntity> findByTournamentAndRound(IndividualEliminationTournamentEntity tournament, Long round) {
        return em.createNamedQuery("Standing.findByTournamentAndRound",
                IndividualEliminationStandingEntity.class).setParameter("tournamentid", tournament.getId()).setParameter("round", round).getResultList();
    }

    @Override
    public void persistStanding(IndividualEliminationTournamentEntity tournament, PersonEntity person) {
        logger.debug("Persisting individual elimination standing...");
        IndividualEliminationStandingEntity ies = new IndividualEliminationStandingEntity(person, 1l, tournament);
        em.persist(ies);
        tournament.getStandings().add(ies);
    }

}
