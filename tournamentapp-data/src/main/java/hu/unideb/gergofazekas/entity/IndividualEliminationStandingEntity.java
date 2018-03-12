/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author gfazekas
 */
@Entity
@DiscriminatorValue(value = "IndividualElimination")
@NamedQueries({
    @NamedQuery(name = "Standing.findIndividualEliminationOne", query = "SELECT s FROM IndividualEliminationStandingEntity s WHERE s.person.id = :pid AND s.tournamentEntity.id = :tid")
})
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
        return "IndividualEliminationStandingEntity{" + "tournament=" + getTournamentEntity().getName() + ", round=" + getRound() + ", person=" + person.getUsername() + '}';
    } 
    
}
