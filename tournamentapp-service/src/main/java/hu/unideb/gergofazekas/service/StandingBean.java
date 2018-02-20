/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.StandingEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
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

    private static final Logger logger = LogManager.getLogger(TournamentBean.class);
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistStanding(IndividualRoundRobinStandingEntity standingEntity) {
        em.persist(standingEntity);
        standingEntity.getPersonEntity().getStandings().add(standingEntity);
        standingEntity.getTournamentEntity().getStandings().add(standingEntity);
    }

    @Override
    public StandingEntity findOne(Long pid, Long tid) {
        return em.createNamedQuery("Standing.findOne", StandingEntity.class).setParameter("pid", pid).setParameter("tid", tid).getSingleResult();
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
        IndividualRoundRobinStandingEntity homeStanding = (IndividualRoundRobinStandingEntity) findOne(individualMatchEntity.getHomeCompetitor().getId(), individualMatchEntity.getTournament().getId());
        IndividualRoundRobinStandingEntity awayStanding =  (IndividualRoundRobinStandingEntity) findOne(individualMatchEntity.getAwayCompetitor().getId(), individualMatchEntity.getTournament().getId());
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
            homeStanding.setLost(homeStanding.getLost()+ 1);
        } else {
            homeStanding.setPoints(homeStanding.getPoints() + irrt.getDrawPoint());
            awayStanding.setPoints(awayStanding.getPoints() + irrt.getDrawPoint());
            homeStanding.setDrawn(homeStanding.getDrawn()+ 1);
            awayStanding.setDrawn(awayStanding.getDrawn()+ 1);
        }
        
    }
        
}
