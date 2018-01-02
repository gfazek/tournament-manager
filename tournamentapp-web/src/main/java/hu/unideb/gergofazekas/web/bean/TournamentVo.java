/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.web.utility.CompetitorType;
import hu.unideb.gergofazekas.web.utility.TournamentType;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
public class TournamentVo {
    
    private String name;
    private TournamentType tournamentType;
    private CompetitorType competitorType;
    private Date start;
    private int winPoint;
    private int drawPoint;
    private int loosePoint;
    private int numberOfCompetitors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public CompetitorType getCompetitorType() {
        return competitorType;
    }

    public void setCompetitorType(CompetitorType competitorType) {
        this.competitorType = competitorType;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public int getWinPoint() {
        return winPoint;
    }

    public void setWinPoint(int winPoint) {
        this.winPoint = winPoint;
    }

    public int getDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(int drawPoint) {
        this.drawPoint = drawPoint;
    }

    public int getLoosePoint() {
        return loosePoint;
    }

    public void setLoosePoint(int loosePoint) {
        this.loosePoint = loosePoint;
    }

    public int getNumberOfCompetitors() {
        return numberOfCompetitors;
    }

    public void setNumberOfCompetitors(int numberOfCompetitors) {
        this.numberOfCompetitors = numberOfCompetitors;
    }
    
}
