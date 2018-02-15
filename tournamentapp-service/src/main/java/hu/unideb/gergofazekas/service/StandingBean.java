/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.StandingEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */
@Stateless
@Named
public class StandingBean implements StandingServiceLocal {

    private static final Logger logger = LogManager.getLogger(TournamentBean.class);
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void persistIndividualRoundRobinStanding(IndividualRoundRobinStandingEntity standingEntity) {
        em.persist(standingEntity);
        standingEntity.getPersonEntity().getStandings().add(standingEntity);
        standingEntity.getTournamentEntity().getStandings().add(standingEntity);
    }

    @Override
    public StandingEntity findOne(Long pid, Long tid) {
        return em.createNamedQuery("Standing.findOne", StandingEntity.class).setParameter("pid", pid).setParameter("tid", tid).getSingleResult();
    }

    @Override
    public void persistIndividualRoundRobinStanding(IndividualRoundRobinTournamentEntity tournament, PersonEntity person) {
        IndividualRoundRobinStandingEntity irrs = new IndividualRoundRobinStandingEntity(person, 0, 0, 0, 0, 0, tournament);
        logger.debug("Persisting Standing: {}", irrs);
        em.persist(irrs);
        tournament.getStandings().add(irrs);
        person.getStandings().add(irrs);
    }
    
}
