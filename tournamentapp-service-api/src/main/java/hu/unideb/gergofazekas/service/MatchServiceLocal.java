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
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**o
 *
 * @author gfazekas
 */
@Local
public interface MatchServiceLocal {
    
    void persistMatch(IndividualMatchEntity matchEntity, IndividualRoundRobinStandingEntity homeStanding, IndividualRoundRobinStandingEntity awayStanding, TournamentEntity tournamentEntity);
    void persistMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualRoundRobinTournamentEntity irrt);
    void persistMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualEliminationTournamentEntity iet, Long round);
    void scheduleMatch(MatchEntity matchEntity, Date time);
    void registerRoundRobinMatchResult(MatchEntity matchEntity, int homeScore, int awayScore);
    void registerMatchResult(IndividualEliminationMatchEntity matchEntity, int homeScore, int awayScore);
    List<IndividualEliminationMatchEntity> getMatchesByRound(Long tournamentId, Long round);
    MatchEntity findOne(Long id);
    List<IndividualMatchEntity> findByCompetitors(IndividualRoundRobinTournamentEntity tournament, PersonEntity homeCompetitor, PersonEntity awayCompetitor);
    
}
