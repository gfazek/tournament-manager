/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "TOURNAMENT")
public class TournamentEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "COMPETITOR_TYPE")
    @Enumerated(EnumType.STRING)
    private CompetitorType competitorType;
    
    @Basic(optional = false)
    @Column(name = "WIN_POINT")
    private int winPoint;
    
    @Basic(optional = false)
    @Column(name = "LOOSE_POINT")
    private int loosePoint;
    
    @Basic(optional = false)
    @Column(name = "DRAW_POINT")
    private int drawPoint;
    
    @OneToMany(mappedBy = "tournament")
    private List<MatchEntity> matches;

    public TournamentEntity() {
        this.matches = new ArrayList<>();
    }

    public TournamentEntity(String name, CompetitorType competitorType, int winPoint, int loosePoint, int drawPoint) {
        this.name = name;
        this.competitorType = competitorType;
        this.winPoint = winPoint;
        this.loosePoint = loosePoint;
        this.drawPoint = drawPoint;
        this.matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompetitorType getCompetitorType() {
        return competitorType;
    }

    public void setCompetitorType(CompetitorType competitorType) {
        this.competitorType = competitorType;
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

    public List<MatchEntity> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
    }
    
}
