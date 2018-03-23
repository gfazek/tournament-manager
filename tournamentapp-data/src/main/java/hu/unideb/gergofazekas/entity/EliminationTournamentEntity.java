/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.TournamentType;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gfazekas
 */
@MappedSuperclass
public abstract class EliminationTournamentEntity extends TournamentEntity {

    @Basic(optional = false)
    @Column(name = "ROUNDS")
    private Long numberOfRounds;
    
    public EliminationTournamentEntity() {
    }

    public EliminationTournamentEntity(Long numberOfRounds, String name, String description, CompetitorType competitorType, TournamentType type, int numberOfCompetitors, Date start) {
        super(name, description, competitorType, type, numberOfCompetitors, start);
        this.numberOfRounds = numberOfRounds;
    }

    public Long getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(Long numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    @Override
    public String toString() {
        return "EliminationTournamentEntity{" + "numberOfRounds=" + numberOfRounds + '}';
    }
    
}
