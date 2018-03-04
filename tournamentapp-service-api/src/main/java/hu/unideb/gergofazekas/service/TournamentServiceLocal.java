/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gfazekas
 */
@Local
public interface TournamentServiceLocal {
    
    void persistTournament(TournamentEntity tournamentEntity);
    void persistCompetitor(IndividualRoundRobinTournamentEntity tournament, PersonEntity personEntity);
    List<TournamentEntity> getTournaments();
    List<TournamentEntity> getOpens();
    TournamentEntity findTournament(Long id);
    void persistEntry(Long tournamentId, String username);
    List<PersonEntity> getIndividualCompetitors(Long id);
    void kickoffRoundRobin(Long id);
    void kickoffElimination(Long id);
    void checkStatus(TournamentEntity tournamentEntity);
    void deleteEntry(Long tournamentId, String username);
}
