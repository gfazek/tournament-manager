/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualEliminationMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.utility.MatchStatus;
import java.util.Date;
import java.util.List;
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

    private static final Logger logger = LogManager.getLogger(MatchBean.class);

    @PersistenceContext
    private EntityManager em;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    @EJB
    private TournamentServiceLocal tournamentServiceLocal;

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
            homeStanding.setLost(homeStanding.getLost() + 1);
        } else {
            homeStanding.setPoints(homeStanding.getPoints() + irrt.getDrawPoint());
            awayStanding.setPoints(awayStanding.getPoints() + irrt.getDrawPoint());
            homeStanding.setDrawn(homeStanding.getDrawn() + 1);
            awayStanding.setDrawn(awayStanding.getDrawn() + 1);
        }
    }

    @Override
    public void persistMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualRoundRobinTournamentEntity irrt) {
        IndividualMatchEntity individualMatchEntity = new IndividualMatchEntity(homeCompetitor, awayCompetitor);
        individualMatchEntity.setTournament(irrt);
        em.persist(individualMatchEntity);
        irrt.getMatches().add(individualMatchEntity);
        individualMatchEntity.getHomeCompetitor().getHomeMatches().add(individualMatchEntity);
        individualMatchEntity.getAwayCompetitor().getAwayMatches().add(individualMatchEntity);
    }

    @Override
    public void persistMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualEliminationTournamentEntity iet, Long round) {
        logger.debug("Persisting individual elimination match...");
        IndividualEliminationMatchEntity match = new IndividualEliminationMatchEntity(homeCompetitor, awayCompetitor, round);
        match.setTournament(iet);
        em.persist(match);
        iet.getMatches().add(match);
    }

    @Override
    public void scheduleMatch(MatchEntity matchEntity, Date time) {
        logger.debug("Schedule match: {}", matchEntity);
        matchEntity.setTime(time);
        matchEntity.setStatus(MatchStatus.SCHEDULED);
        MatchEntity tmp = em.merge(matchEntity);
    }

    @Override
    public void registerRoundRobinMatchResult(MatchEntity matchEntity, int homeScore, int awayScore) {
        logger.debug("Register round robin match result: {}", matchEntity);
        matchEntity.setHomeScore(homeScore);
        matchEntity.setAwayScore(awayScore);
        matchEntity.setStatus(MatchStatus.FINISHED);
        MatchEntity tmp = em.merge(matchEntity);
        standingServiceLocal.updateStandings((IndividualMatchEntity) matchEntity);
        tournamentServiceLocal.checkStatus(matchEntity.getTournament());
    }

    @Override
    public void registerMatchResult(IndividualEliminationMatchEntity matchEntity, int homeScore, int awayScore) {
        logger.debug("Register individual elimination match result: {}", matchEntity);
        matchEntity.setHomeScore(homeScore);
        matchEntity.setAwayScore(awayScore);
        matchEntity.setStatus(MatchStatus.FINISHED);
        em.merge(matchEntity);
        standingServiceLocal.updateStandings(matchEntity);
        tournamentServiceLocal.registerNextRound((IndividualEliminationTournamentEntity) matchEntity.getTournament(), matchEntity.getRound());
    }

    @Override
    public MatchEntity findOne(Long id) {
        return em.find(MatchEntity.class, id);
    }

    @Override
    public List<IndividualEliminationMatchEntity> getMatchesByRound(Long tournamentId, Long round) {
        return em.createNamedQuery("IndividualEliminationMatch.getMatchesByRound", IndividualEliminationMatchEntity.class).setParameter("tournamentid", tournamentId).setParameter("round", round).getResultList();
    }

    @Override
    public List<IndividualMatchEntity> findByCompetitors(IndividualRoundRobinTournamentEntity tournament, PersonEntity homeCompetitor, PersonEntity awayCompetitor) {
        return em.createNamedQuery("IndividualMatchEntity.findByCompetitors", IndividualMatchEntity.class).setParameter("tournamentid", tournament.getId()).setParameter("homecompetitorid", homeCompetitor.getId()).setParameter("awaycompetitorid", awayCompetitor.getId()).getResultList();
    }

}
