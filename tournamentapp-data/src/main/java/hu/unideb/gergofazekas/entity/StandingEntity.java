/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "STANDING")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQueries({
    @NamedQuery(name = "Standing.findByTournament", query = "SELECT s FROM StandingEntity s WHERE s.tournamentEntity.id = :id"),
    @NamedQuery(name = "Standing.findByTournamentAndRound", 
            query = "SELECT s FROM IndividualEliminationStandingEntity s WHERE s.tournamentEntity.id = :tournamentid AND s.round = :round")
})
public abstract class StandingEntity extends BaseEntity implements Serializable {
    
    @ManyToOne
    private TournamentEntity tournamentEntity;

    public StandingEntity() {
    }

    public StandingEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }
    
    public TournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }

    @Override
    public String toString() {
        return "StandingEntity{" + "tournamentEntity=" + tournamentEntity.getName() + '}';
    }
    
}
