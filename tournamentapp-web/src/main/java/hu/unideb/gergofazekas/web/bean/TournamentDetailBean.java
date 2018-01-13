/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.TournamentEntity;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class TournamentDetailBean implements Serializable {

    private TournamentEntity tournamentEntity;
    
    public TournamentDetailBean() {
    }

    public TournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }
    
}
