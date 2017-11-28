/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "MATCH")
public class MatchEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "HOME_COMPETITOR")
    private int homeCompetitorId;
    
    @Basic(optional = false)
    @Column(name = "AWAY_COMPETITOR")
    private int awayCompetitorId;
    
    @Basic(optional = false)
    @Column(name = "HOME_SCORE")
    private int homeScore;
    
    @Basic(optional = false)
    @Column(name = "AWAY_SCORE")
    private int awayScore;
    
    @ManyToOne
    private TournamentEntity tournament;

    public MatchEntity() {
    }

    public MatchEntity(int homeCompetitorId, int awayCompetitorId, int homeScore, int awayScore) {
        this.homeCompetitorId = homeCompetitorId;
        this.awayCompetitorId = awayCompetitorId;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getHomeCompetitorId() {
        return homeCompetitorId;
    }

    public void setHomeCompetitorId(int homeCompetitorId) {
        this.homeCompetitorId = homeCompetitorId;
    }

    public int getAwayCompetitorId() {
        return awayCompetitorId;
    }

    public void setAwayCompetitorId(int awayCompetitorId) {
        this.awayCompetitorId = awayCompetitorId;
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

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }
    
}
