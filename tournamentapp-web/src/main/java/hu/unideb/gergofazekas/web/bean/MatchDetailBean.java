/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.service.MatchServiceLocal;
import java.io.Serializable;
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
public class MatchDetailBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(MatchDetailBean.class);
    private MatchEntity matchEntity;
    private int homeScore, awayScore;
    
    @EJB
    private MatchServiceLocal matchServiceLocal;

    public MatchDetailBean() {
    }
    
    @PostConstruct
    public void init() {
        matchEntity = matchServiceLocal.findOne(new Long(1));
    }
    
    public void registerResult() {
        logger.debug("lkurvaanyad");
        matchServiceLocal.registerResult(matchEntity, 0, 0);
    }

    public MatchEntity getMatchEntity() {
        return (IndividualMatchEntity) matchEntity;
    }

    public void setMatchEntity(MatchEntity matchEntity) {
        this.matchEntity = matchEntity;
    }

    public MatchServiceLocal getMatchServiceLocal() {
        return matchServiceLocal;
    }

    public void setMatchServiceLocal(MatchServiceLocal matchServiceLocal) {
        this.matchServiceLocal = matchServiceLocal;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
    
}
