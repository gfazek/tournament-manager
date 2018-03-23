/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualEliminationMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.MatchStatus;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import hu.unideb.gergofazekas.utility.TournamentType;
import java.util.ArrayList;
import java.util.Collections;
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
public class TournamentBean implements TournamentServiceLocal {

    private static final Logger logger = LogManager.getLogger(TournamentBean.class);

    @EJB
    private PersonServiceLocal personServiceLocal;

    @EJB
    private MatchServiceLocal matchServiceLocal;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persistTournament(TournamentEntity tournamentEntity) {
        em.persist(tournamentEntity);
        em.flush();
    }

    @Override
    public void persistCompetitor(IndividualRoundRobinTournamentEntity tournament, PersonEntity personEntity) {
        logger.debug("Persisting individual competitor: tournament: {} | person: {}", tournament, personEntity);
        tournament.getPeople().add(personEntity);
        personEntity.getRoundRobinTournaments().add(tournament);
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
//        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
        if (isIndividualRoundRobin(tournamentEntity)) {
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
        int numberOfCompetitors = 0;
        if (isIndividualRoundRobin(tournamentEntity)) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            tmp.getPeople().add(personEntity);
            numberOfCompetitors = tmp.getPeople().size();
            personEntity.getRoundRobinTournaments().add((IndividualRoundRobinTournamentEntity) tournamentEntity);
        } else if (isIndividualElimination(tournamentEntity)) {
            IndividualEliminationTournamentEntity iet = (IndividualEliminationTournamentEntity) tournamentEntity;
            iet.getPeople().add(personEntity);
            numberOfCompetitors = iet.getPeople().size();
        }
        if (numberOfCompetitors == tournamentEntity.getNumberOfCompetitors()) {
            tournamentEntity.setStatus(TournamentStatus.WAITING_FOR_KICKOFF);
        }
    }

    @Override
    public List<PersonEntity> getIndividualCompetitors(Long id, TournamentType tournamentType) {
        List<PersonEntity> competitors = new ArrayList<>();
        if (tournamentType == TournamentType.ELIMINATION) {
            competitors = em.createNamedQuery("IndividualEliminationTournament.findCompetitors", PersonEntity.class).setParameter("id", id).getResultList();
        } else if (tournamentType == TournamentType.ROUNDROBIN) {
            competitors = em.createNamedQuery("IndividualRoundRobinTournament.findCompetitors", PersonEntity.class).setParameter("id", id).getResultList();
        }
        if (competitors.get(0) == null && competitors.size() == 1) {
            return new ArrayList<>();
        }
        return competitors;
    }

    @Override
    public void kickoffRoundRobin(Long id) {
        TournamentEntity tournamentEntity = findTournament(id);
        logger.debug("Kicking off: {}", tournamentEntity);
        tournamentEntity.setStatus(TournamentStatus.IN_PROGRESS);
        if (isIndividualRoundRobin(tournamentEntity)) {
            IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            List<PersonEntity> competitors = irrt.getPeople();
            logger.debug("Instantiating matches for competitors: {}", competitors);
            for (PersonEntity p1 : competitors) {
                logger.debug("p1: {}", p1);
                standingServiceLocal.persistStanding(irrt, p1);
                List<PersonEntity> tmp = competitors.subList(competitors.indexOf(p1) + 1, competitors.size());
                logger.debug("tmp: {}", tmp);
                for (PersonEntity p2 : tmp) {
                    logger.debug("p2: {}", p2);
                    matchServiceLocal.persistMatch(p1, p2, irrt);
                }
            }
        }
    }

    @Override
    public void kickoffElimination(Long id) {
        TournamentEntity tournamentEntity = findTournament(id);
        tournamentEntity.setStatus(TournamentStatus.IN_PROGRESS);
        if (tournamentEntity.getCompetitorType() == CompetitorType.PLAYER) {
            IndividualEliminationTournamentEntity iet = (IndividualEliminationTournamentEntity) tournamentEntity;
            List<PersonEntity> competitors = iet.getPeople();
            for (PersonEntity competitor : competitors) {
                standingServiceLocal.persistStanding(iet, competitor);
            }
            Collections.shuffle(competitors);
            for (int i = 0; i < competitors.size() - 1; i = i + 2) {
                matchServiceLocal.persistMatch(competitors.get(i), competitors.get(i + 1), iet, 1l);
            }
        }
    }

    @Override
    public void checkStatus(TournamentEntity tournamentEntity) {
        logger.debug("Em contains tournsmententity?: {}", em.contains(tournamentEntity));
        List<MatchEntity> matches = tournamentEntity.getMatches();
        for (MatchEntity match : matches) {
            if (match.getStatus() == MatchStatus.NEW || match.getStatus() == MatchStatus.SCHEDULED) {
                return;
            }
        }
        tournamentEntity.setStatus(TournamentStatus.CLOSED);
        em.merge(tournamentEntity);
    }

    @Override
    public void deleteEntry(Long tournamentId, String username) {
        TournamentEntity tournamentEntity = findTournament(tournamentId);
        PersonEntity personEntity = personServiceLocal.findByUsername(username);
        if (isIndividualRoundRobin(tournamentEntity)) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            tmp.getPeople().remove(personEntity);
            personEntity.getRoundRobinTournaments().remove((IndividualRoundRobinTournamentEntity) tournamentEntity);
        } else if (isIndividualElimination(tournamentEntity)) {
            IndividualEliminationTournamentEntity iet = (IndividualEliminationTournamentEntity) tournamentEntity;
            iet.getPeople().remove(personEntity);
            logger.debug("after remove: {}", iet.getPeople());
        }
    }

    @Override
    public void registerNextRound(IndividualEliminationTournamentEntity tournament, Long round) {
        logger.debug("Managing next round: {}", tournament);
        if (isFinishedRound(tournament.getId(), round)) {
            List<IndividualEliminationStandingEntity> standings = standingServiceLocal.findByTournamentAndRound(tournament, round + 1);
            List<PersonEntity> competitors = new ArrayList<>();
            for (IndividualEliminationStandingEntity standing : standings) {
                competitors.add(standing.getPerson());
            }
            if (competitors.size() == 1) {
                close(tournament);
                return;
            }
            Collections.shuffle(competitors);
            for (int i = 0; i < competitors.size() - 1; i = i + 2) {
                matchServiceLocal.persistMatch(competitors.get(i), competitors.get(i + 1), tournament, round + 1);
            }
        }
    }

    @Override
    public void close(TournamentEntity tournamentEntity) {
        logger.debug("Closing tournament: {}", tournamentEntity);
        tournamentEntity.setStatus(TournamentStatus.CLOSED);
        em.merge(tournamentEntity); // MAYBE IT'S NECESSARY
    }

    @Override
    public boolean isFinishedRound(Long tournamentId, Long round) {
        List<IndividualEliminationMatchEntity> matches = matchServiceLocal.getMatchesByRound(tournamentId, round);
        for (IndividualEliminationMatchEntity match : matches) {
            if (match.getStatus() != MatchStatus.FINISHED) {
                return false;
            }
        }
        return true;
    }

    private boolean isIndividualRoundRobin(TournamentEntity tournamentEntity) {
        return tournamentEntity.getType() == TournamentType.ROUNDROBIN
                && tournamentEntity.getCompetitorType() == CompetitorType.PLAYER;
    }

    private boolean isIndividualElimination(TournamentEntity tournamentEntity) {
        return tournamentEntity.getType() == TournamentType.ELIMINATION
                && tournamentEntity.getCompetitorType() == CompetitorType.PLAYER;
    }

}
