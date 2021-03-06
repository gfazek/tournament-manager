/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.TournamentType;
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
@DiscriminatorValue(value = "IndividualElimination")
@NamedQueries({
    @NamedQuery(name = "IndividualEliminationTournament.findCompetitors", query = "SELECT t.people FROM IndividualEliminationTournamentEntity t WHERE t.id = :id"),
})
public class IndividualEliminationTournamentEntity extends EliminationTournamentEntity {

    @ManyToMany
    @JoinTable(name = "PERSON_ELIMINATION", joinColumns = {
        @JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")})
    private List<PersonEntity> people;
    
    public IndividualEliminationTournamentEntity() {
    }

    public IndividualEliminationTournamentEntity(Long numberOfRounds, String name, String description, int numberOfCompetitors, Date start) {
        super(numberOfRounds, name, description, CompetitorType.PLAYER, TournamentType.ELIMINATION, numberOfCompetitors, start);
        this.people = new ArrayList<>();
    }

   

    public List<PersonEntity> getPeople() {
        return people;
    }

    public void setPeople(List<PersonEntity> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IndividualEliminationTournamentEntity{");
        sb.append("name=").append(getName()).append(", ");
        sb.append("people=");
        people.forEach(p -> {
            sb.append(p.getUsername()).append(",");
        });
        sb.append("}");
        return sb.toString();
    }
    
}
