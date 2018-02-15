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
public class MatchBean implements MatchServiceLocal {

    private static final Logger logger = LogManager.getLogger(PersonBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    @Override
    public void persistMatch(IndividualMatchEntity individualMatchEntity, IndividualRoundRobinStandingEntity homeStanding, IndividualRoundRobinStandingEntity awayStanding, TournamentEntity tournamentEntity) {
        individualMatchEntity.setTournament(tournamentEntity);
        em.persist(individualMatchEntity);
        PersonEntity homePerson = individualMatchEntity.getHomeCompetitor();
        PersonEntity awayPerson = individualMatchEntity.getAwayCompetitor();
        tournamentEntity.getMatches().add(individualMatchEntity);
        homePerson.getHomeMatches().add(individualMatchEntity);
        awayPerson.getAwayMatches().add(individualMatchEntity);
        
        //Should be replaced, because it's specific
        IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) tournamentEntity;

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

    @Override
    public void persistIndividualMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualRoundRobinTournamentEntity irrt) {
        IndividualMatchEntity individualMatchEntity = new IndividualMatchEntity(homeCompetitor, awayCompetitor);
        individualMatchEntity.setTournament(irrt);
        em.persist(individualMatchEntity);
        irrt.getMatches().add(individualMatchEntity);
        individualMatchEntity.getHomeCompetitor().getHomeMatches().add(individualMatchEntity);
        individualMatchEntity.getAwayCompetitor().getAwayMatches().add(individualMatchEntity);
    }

}
