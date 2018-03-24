/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.DrawStrategy;
import hu.unideb.gergofazekas.web.utility.CompetitorType;
import hu.unideb.gergofazekas.web.utility.TournamentType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */
@Named
@ViewScoped
public class TournamentRegistrationBean implements Serializable {
    
    private static final Logger logger = LogManager.getLogger(TournamentRegistrationBean.class);
    
    private TournamentEntity tournamentEntity;
    private CompetitorType[] competitorTypes;
    private TournamentType[] tournamentTypes;
    private TournamentType tournamentType;
    private TournamentVo tournamentVo;
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    
    public TournamentRegistrationBean() {
    }
    
    @PostConstruct
    public void init() {
        tournamentVo = new TournamentVo();
        tournamentVo.getDrawStrategy().add("More Wins");
        tournamentVo.getDrawStrategy().add("Better goal ratio");
        tournamentVo.getDrawStrategy().add("Winner of the battle");
    }
    
    public String createTournament() {
        if (tournamentType == TournamentType.ROUNDROBIN 
                && tournamentVo.getCompetitorType() == CompetitorType.PLAYER) {
            tournamentEntity = new IndividualRoundRobinTournamentEntity(tournamentVo.getWinPoint(), tournamentVo.getLoosePoint(), tournamentVo.getDrawPoint(), 
                    tournamentVo.getName(), tournamentVo.getDescription(), tournamentVo.getNumberOfCompetitors(), tournamentVo.getStart(), getDrawStrategyMap(tournamentVo.getDrawStrategy()));
        } else if (tournamentType == TournamentType.ELIMINATION) {
            tournamentEntity = new IndividualEliminationTournamentEntity(calculateNumberOfRounds(tournamentVo.getNumberOfCompetitors()), tournamentVo.getName(), 
                    tournamentVo.getDescription(), tournamentVo.getNumberOfCompetitors(), tournamentVo.getStart());
        }
        tournamentServiceLocal.persistTournament(tournamentEntity);
        return "index";
    }
    
    public Map<Integer, DrawStrategy> getDrawStrategyMap(List<String> drawstrategy) {
        Map<Integer, DrawStrategy> map = new TreeMap<>();
        int key = 1;
        for (String value : drawstrategy) {
            switch (value) {
                case "More Wins":
                    map.put(key, DrawStrategy.WIN);
                    key++;
                    break;
                case "Better goal ratio":
                    map.put(key, DrawStrategy.SCORE);
                    key++;
                    break;
                case "Winner of the battle":
                    map.put(key, DrawStrategy.BATTLE);
                    key++;
                    break;
            }
        }
        return map;
    }
    
    public Long calculateNumberOfRounds(int numberOfCompetitors) {
        return (long)(Math.log(numberOfCompetitors) / Math.log(2));
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

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }
    
}
