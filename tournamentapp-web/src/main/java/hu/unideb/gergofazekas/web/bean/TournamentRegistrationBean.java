/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.RoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.web.utility.CompetitorType;
import hu.unideb.gergofazekas.web.utility.TournamentType;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class TournamentRegistrationBean {
    
    private static final Logger logger = LogManager.getLogger(TournamentRegistrationBean.class);
    
    private TournamentEntity tournamentEntity;
    private CompetitorType[] competitorTypes;
    private TournamentType[] tournamentTypes;
    private TournamentVo tournamentVo;
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    
    public TournamentRegistrationBean() {
    }
    
    @PostConstruct
    public void init() {
        tournamentVo = new TournamentVo();
    }
    
    public String createTournament() {
        if (tournamentVo.getTournamentType() == TournamentType.ROUNDROBIN 
                && tournamentVo.getCompetitorType() == CompetitorType.PLAYER) {
            tournamentEntity = new IndividualRoundRobinTournamentEntity(tournamentVo.getName(), tournamentVo.getDescription(), tournamentVo.getNumberOfCompetitors(), tournamentVo.getStart(), 
                    tournamentVo.getWinPoint(), tournamentVo.getDrawPoint(), tournamentVo.getLoosePoint());
        }
        tournamentServiceLocal.persistTournament(tournamentEntity);
        return "index";
    }

    public TournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }

    public TournamentVo getTournamentVo() {
        return tournamentVo;
    }

    public void setTournamentVo(TournamentVo tournamentVo) {
        this.tournamentVo = tournamentVo;
    }

    public CompetitorType[] getCompetitorTypes() {
        return CompetitorType.values();
    }
    
    public TournamentType[] getTournamentTypes() {
        return TournamentType.values();
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }
    
}
