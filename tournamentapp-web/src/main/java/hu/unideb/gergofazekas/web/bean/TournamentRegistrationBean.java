/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.CompetitorType;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class TournamentRegistrationBean {

    private TournamentEntity tournamentEntity;
    private CompetitorType[] competitorTypes;
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    
    public TournamentRegistrationBean() {
    }
    
    @PostConstruct
    public void init() {
        tournamentEntity = new TournamentEntity();
    }
    
    public String createTournament() {
        tournamentServiceLocal.persistTournament(tournamentEntity);
        return "index";
    }

    public TournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }

    public CompetitorType[] getCompetitorTypes() {
        return CompetitorType.values();
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }
    
}
