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

/**
 *
 * @author gfazekas
 */
@Entity
@DiscriminatorValue(value = "TeamRoundRobin")
public class TeamRoundRobinTournamentEntity extends RoundRobinTournamentEntity {
    
    @ManyToMany
     @JoinTable(name = "TEAM_ROUNDROBIN", joinColumns = {
        @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TOURNAMENT_ID", referencedColumnName = "ID")})
    private List<TeamEntity> teams;

    public TeamRoundRobinTournamentEntity() {
    }
    
    public TeamRoundRobinTournamentEntity(String name, String description, int numberOfCompetitors, Date start, int winPoint, int drawPoint, int loosePoint) {
        super(name, description, numberOfCompetitors, start, winPoint, drawPoint, loosePoint);
        teams = new ArrayList<>();
    }

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }
    
}
