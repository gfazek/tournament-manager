/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
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
    
    void persistIndividualRoundRobinStanding(IndividualRoundRobinStandingEntity irrs);
    void persistIndividualRoundRobinStanding(IndividualRoundRobinTournamentEntity tournament, PersonEntity person);
    StandingEntity findOne(Long pid, Long tid);
    
}
