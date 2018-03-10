/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author gfazekas
 */
@Entity
@DiscriminatorValue(value = "Individual")
public class IndividualMatchEntity extends MatchEntity {
    
    @ManyToOne
    private PersonEntity homeCompetitor;
    
    @ManyToOne
    private PersonEntity awayCompetitor;

    public IndividualMatchEntity() {
    }

    public IndividualMatchEntity(int homeScore, int awayScore, PersonEntity homeCompetitor, PersonEntity awayCompetitor) {
        super(homeScore, awayScore);
        this.homeCompetitor = homeCompetitor;
        this.awayCompetitor = awayCompetitor;
    }
    
    public IndividualMatchEntity(PersonEntity homeCompetitor, PersonEntity awayCompetitor) {
        this.homeCompetitor = homeCompetitor;
        this.awayCompetitor = awayCompetitor;
    }

    public PersonEntity getHomeCompetitor() {
        return homeCompetitor;
    }

    public void setHomeCompetitor(PersonEntity homeCompetitor) {
        this.homeCompetitor = homeCompetitor;
    }

    public PersonEntity getAwayCompetitor() {
        return awayCompetitor;
    }

    public void setAwayCompetitor(PersonEntity awayCompetitor) {
        this.awayCompetitor = awayCompetitor;
    }

    @Override
    public String toString() {
        return "IndividualMatchEntity{" + "homeScore=" + getHomeScore() + ", awayScore=" + getAwayScore() + ", homeCompetitor=" + homeCompetitor.getUsername() + ", awayCompetitor=" + awayCompetitor.getUsername() + '}';
    }
    
}
