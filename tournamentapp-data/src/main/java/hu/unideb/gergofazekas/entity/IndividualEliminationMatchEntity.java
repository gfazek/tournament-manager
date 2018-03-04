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
@DiscriminatorValue(value = "IndividualElimination")
public class IndividualEliminationMatchEntity extends EliminationMatchEntity {
    
    @ManyToOne
    private PersonEntity homeCompetitor;
    
    @ManyToOne
    private PersonEntity awayCompetitor;

    public IndividualEliminationMatchEntity() {
    }

    public IndividualEliminationMatchEntity(PersonEntity homeCompetitor, PersonEntity awayCompetitor, Long round) {
        super(round);
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
    
}
