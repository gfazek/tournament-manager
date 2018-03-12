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
import javax.ejb.Local;

/**
 *
 * @author gfazekas
 */
@Local
public interface StandingServiceLocal {
    
    void persistStanding(IndividualRoundRobinStandingEntity irrs);
    void persistStanding(IndividualRoundRobinTournamentEntity tournament, PersonEntity person);
    void persistStanding(IndividualEliminationTournamentEntity tournament, PersonEntity person);
    IndividualRoundRobinStandingEntity findIndividualRoundRobinOne(Long pid, Long tid);
    IndividualEliminationStandingEntity findIndividualEliminationOne(Long pid, Long tid);
    List<StandingEntity> findByTournament(Long tournamentId);
    List<IndividualEliminationStandingEntity> findByTournamentAndRound(IndividualEliminationTournamentEntity tournament, Long round);
    void updateStandings(IndividualMatchEntity individualMatchEntity);
    void updateStandings(IndividualEliminationMatchEntity individualEliminationMatchEntity);
}
