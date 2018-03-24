/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.web.utility.CompetitorType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gfazekas
 */
public class TournamentVo {
    
    private String name;
    private String description;
    private CompetitorType competitorType;
    private Date start;
    private int winPoint;
    private int drawPoint;
    private int loosePoint;
    private int numberOfCompetitors;
    private List<String> drawStrategy = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getDrawStrategy() {
        return drawStrategy;
    }

    public void setDrawStrategy(List<String> drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
    
}
