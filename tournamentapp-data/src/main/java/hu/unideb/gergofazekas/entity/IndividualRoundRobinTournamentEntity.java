/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author gfazekas
 */
@Entity
@DiscriminatorValue(value = "IndividualRoundRobin")
@NamedQueries({
    @NamedQuery(name = "IndividualRoundRobinTournament.findCompetitors", query = "SELECT t.people FROM IndividualRoundRobinTournamentEntity t WHERE t.id = :id"),
})
public class IndividualRoundRobinTournamentEntity extends RoundRobinTournamentEntity {
    
    @ManyToMany
    @JoinTable(name = "PERSON_ROUNDROBIN", joinColumns = {
        @JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")})
    private List<PersonEntity> people;

    public IndividualRoundRobinTournamentEntity() {
        this.people = new ArrayList<>();
    }
    
    public IndividualRoundRobinTournamentEntity(String name, String description, int numberOfCompetitors, Date start, int winPoint, int drawPoint, int loosePoint) {
        super(name, description, numberOfCompetitors, start, winPoint, drawPoint, loosePoint);
        people = new ArrayList<>();
    }

    public List<PersonEntity> getPeople() {
        return this.people;
    }

    public void setPeople(List<PersonEntity> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "IndividualRoundRobinTournamentEntity{"  + "name=" + getName() + ", description=" + getDescription() + ", status=" + getStatus() + ", numberOfCompetitors=" + getNumberOfCompetitors() + ", start=" + getStart() + ", matches=" + getMatches() + ", winPoint=" + getWinPoint() + ", loosePoint=" + getLoosePoint() + ", drawPoint=" + getDrawPoint() + ", people=" + people + '}';
    }
    
    
}
