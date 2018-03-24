/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.DrawStrategy;
import hu.unideb.gergofazekas.utility.TournamentType;
import java.util.Date;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKeyColumn;
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

    @ElementCollection
    @CollectionTable(name = "ROUNDROBIN_DRAWSTRATEGY")
    @Enumerated(EnumType.STRING)
    @MapKeyColumn(name = "PRIORITY")
    @Column(name = "DRAW_STRATEGY")
    private Map<Integer, DrawStrategy> drawStrategy;

    public RoundRobinTournamentEntity() {
    }

    public RoundRobinTournamentEntity(int winPoint, int loosePoint, int drawPoint, String name, String description, CompetitorType competitorType, TournamentType type, int numberOfCompetitors, Date start, Map<Integer, DrawStrategy> drawStrategy) {
        super(name, description, competitorType, type, numberOfCompetitors, start);
        this.winPoint = winPoint;
        this.loosePoint = loosePoint;
        this.drawPoint = drawPoint;
        this.drawStrategy = drawStrategy;
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

    public Map<Integer, DrawStrategy> getDrawStrategy() {
        return drawStrategy;
    }

    public void setDrawStrategy(Map<Integer, DrawStrategy> drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
    
    @Override
    public String toString() {
        return "RoundRobinTournamentEntity{" + "winPoint=" + winPoint + ", loosePoint=" + loosePoint + ", drawPoint=" + drawPoint + '}';
    }

}
