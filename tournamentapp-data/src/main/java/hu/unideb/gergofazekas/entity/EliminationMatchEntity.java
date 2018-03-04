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
public abstract class EliminationMatchEntity extends MatchEntity {

    @Basic(optional = false)
    @Column(name = "ROUNDS")
    private Long round;
    
    public EliminationMatchEntity() {
    }

    public EliminationMatchEntity(Long round) {
        super();
        this.round = round;
    }

    public Long getRound() {
        return round;
    }

    public void setRound(Long round) {
        this.round = round;
    }
    
}
