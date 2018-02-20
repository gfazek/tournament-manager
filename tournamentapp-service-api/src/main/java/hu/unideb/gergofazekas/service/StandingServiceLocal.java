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

/**
 *
 * @author gfazekas
 */
@Local
public interface StandingServiceLocal {
    
    void persistStanding(IndividualRoundRobinStandingEntity irrs);
    void persistStanding(IndividualRoundRobinTournamentEntity tournament, PersonEntity person);
    StandingEntity findOne(Long pid, Long tid);
    void updateStandings(IndividualMatchEntity individualMatchEntity);
}
