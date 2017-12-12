/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.TournamentEntity;
import javax.ejb.Local;

/**
 *
 * @author gfazekas
 */
@Local
public interface TournamentServiceLocal {
    
    void persistTournament(TournamentEntity tournamentEntity);
    
}
