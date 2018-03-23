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
public abstract class EliminationStandingEntity extends StandingEntity {
    
    @Basic(optional = false)
    @Column(name = "ROUND")
    private Long round;

    public EliminationStandingEntity() {
    }

    public EliminationStandingEntity(Long round, TournamentEntity tournamentEntity) {
        super(tournamentEntity);
        this.round = round;
    }

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return "EliminationStandingEntity{" + "tournamentEntity=" + getTournamentEntity().getName() + ", round=" + round + '}';
    }
    
}
