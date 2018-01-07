/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
@Named
@ViewScoped
public class OpenTournamentsBean implements Serializable {

    private List<TournamentEntity> tournaments;
    private List<TournamentEntity> filteredTournaments;
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    public OpenTournamentsBean() {
    }
    
    @PostConstruct
    public void init() {
        tournaments = tournamentServiceLocal.getOpens();
    }

    public List<TournamentEntity> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TournamentEntity> tournaments) {
        this.tournaments = tournaments;
    }

    public List<TournamentEntity> getFilteredTournaments() {
        return filteredTournaments;
    }

    public void setFilteredTournaments(List<TournamentEntity> filteredTournaments) {
        this.filteredTournaments = filteredTournaments;
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }
    
}
