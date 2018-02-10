/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gfazekas
 */
@MappedSuperclass
public class RoundRobinStandingEntity extends StandingEntity {
    
    @Basic(optional = false)
    @Column(name = "PLAYED")
    private int played;
    
    @Basic(optional = false)
    @Column(name = "WON")
    private int won;
    
    @Basic(optional = false)
    @Column(name = "DRAWN")
    private int drawn;
    
    @Basic(optional = false)
    @Column(name = "LOST")
    private int lost;
    
    @Basic(optional = false)
    @Column(name = "POINTS")
    private int points;

    public RoundRobinStandingEntity() {
    }

    public RoundRobinStandingEntity(int played, int won, int drawn, int lost, int points, TournamentEntity tournamentEntity) {
        super(tournamentEntity);
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.points = points;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "RoundRobinStandingEntity{" + "tournamentEntity=" + getTournamentEntity() + ", played=" + played + ", won=" + won + ", drawn=" + drawn + ", lost=" + lost + ", points=" + points + '}';
    }
    
    
    
}
