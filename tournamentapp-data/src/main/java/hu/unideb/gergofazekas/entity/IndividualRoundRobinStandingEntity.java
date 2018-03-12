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
@DiscriminatorValue(value = "IndividualRoundRobin")
@NamedQueries({
    @NamedQuery(name = "Standing.findIndividualRoundRobinOne", query = "SELECT s FROM IndividualRoundRobinStandingEntity s WHERE s.person.id = :pid AND s.tournamentEntity.id = :tid")
})
public class IndividualRoundRobinStandingEntity extends RoundRobinStandingEntity {

    @ManyToOne
    private PersonEntity person;

    public IndividualRoundRobinStandingEntity() {
    }

    public IndividualRoundRobinStandingEntity(PersonEntity person, int played, int won, int drawn, int lost, int points, TournamentEntity tournamentEntity) {
        super(played, won, drawn, lost, points, tournamentEntity);
        this.person = person;
    }

    public PersonEntity getPersonEntity() {
        return person;
    }

    public void setPersonEntity(PersonEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "IndividualRoundRobinStandingEntity{" + "tournamentEntity=" + getTournamentEntity().getName() + ", played=" + getPlayed() + ", won=" + getWon() + ", drawn=" + getDrawn() + ", lost=" + getLost() + ", points=" + getPoints() + ", person=" + person.getUsername() + '}';
    }

}
