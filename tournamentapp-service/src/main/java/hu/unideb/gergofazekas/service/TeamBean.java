/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TeamEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class TeamBean implements TeamServiceLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void persistTeam(TeamEntity team, PersonEntity person) {
        person.getTeams().add(team);
        em.merge(person);
        team.getPlayers().add(person);
        em.persist(team);
    }
}
