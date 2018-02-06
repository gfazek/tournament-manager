/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gfazekas
 */
@MappedSuperclass
public abstract class RoundRobinTournamentEntity extends TournamentEntity {
    
    @Basic(optional = false)
    @Column(name = "WIN_POINT")
    private int winPoint;
    
    @Basic(optional = false)
    @Column(name = "LOOSE_POINT")
    private int loosePoint;
    
    @Basic(optional = false)
    @Column(name = "DRAW_POINT")
    private int drawPoint;

    public RoundRobinTournamentEntity() {
    }
    
    public RoundRobinTournamentEntity(String name, String description, int numberOfCompetitors, Date start, int winPoint, int drawPoint, int loosePoint) {
        super(name, description, numberOfCompetitors, start);
        this.winPoint = winPoint;
        this.loosePoint = loosePoint;
        this.drawPoint = drawPoint;
    }

    public int getWinPoint() {
        return winPoint;
    }

    public void setWinPoint(int winPoint) {
        this.winPoint = winPoint;
    }

    public int getLoosePoint() {
        return loosePoint;
    }

    public void setLoosePoint(int loosePoint) {
        this.loosePoint = loosePoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }

    @Override
    public String toString() {
        return "RoundRobinTournamentEntity{" + "winPoint=" + winPoint + ", loosePoint=" + loosePoint + ", drawPoint=" + drawPoint + '}';
    }
    
}
