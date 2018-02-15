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
import javax.ejb.Local;

/**o
 *
 * @author gfazekas
 */
@Local
public interface MatchServiceLocal {
    
    void persistMatch(IndividualMatchEntity matchEntity, IndividualRoundRobinStandingEntity homeStanding, IndividualRoundRobinStandingEntity awayStanding, TournamentEntity tournamentEntity);
    void persistIndividualMatch(PersonEntity homeCompetitor, PersonEntity awayCompetitor, IndividualRoundRobinTournamentEntity irrt);
    
}
