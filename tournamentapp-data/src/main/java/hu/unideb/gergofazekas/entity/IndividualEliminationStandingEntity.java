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
public class IndividualEliminationStandingEntity extends EliminationStandingEntity {

   @ManyToOne
   private PersonEntity person;
    
    public IndividualEliminationStandingEntity() {
    }

    public IndividualEliminationStandingEntity(PersonEntity person, Long round, TournamentEntity tournamentEntity) {
        super(round, tournamentEntity);
        this.person = person;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "IndividualEliminationStandingEntity{" + "tournamentEntity=" + getTournamentEntity() + ", round=" + getRound() + ", person=" + person + '}';
    } 
    
}
