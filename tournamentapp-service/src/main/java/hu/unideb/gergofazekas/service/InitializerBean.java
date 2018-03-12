/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.entity.TeamEntity;
import hu.unideb.gergofazekas.entity.TeamRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.MatchStatus;
import hu.unideb.gergofazekas.utility.Role;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author gfazekas
 */
@Startup
@Singleton
public class InitializerBean {

    private static final Logger logger = LogManager.getLogger(InitializerBean.class);

    @EJB
    private RoleServiceLocal roleServiceLocal;

    @EJB
    private PersonServiceLocal personServiceLocal;

    @EJB
    private TeamServiceLocal teamServiceLocal;

    @EJB
    private TournamentServiceLocal tournamentServiceLocal;

    @EJB
    private MatchServiceLocal matchServiceLocal;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    @PostConstruct
    public void init() {
        logger.info("Persisting intial objects to the database");
        RoleEntity userRole = new RoleEntity(Role.USER);
        RoleEntity adminRole = new RoleEntity(Role.ADMIN);
        RoleEntity supervisorRole = new RoleEntity(Role.SUPERVISOR);
        roleServiceLocal.persistRole(userRole);
        roleServiceLocal.persistRole(adminRole);
        roleServiceLocal.persistRole(supervisorRole);
        PersonEntity personEntity1 = new PersonEntity.PersonBuilder().username("geri").email("romain.hoogmoed@example.com").password("pass")
                .firstName("Romain").lastName("Hoogmoed").gender(Gender.MALE).dob(calendarToDate(1981, 5, 9)).createPerson();
        PersonEntity personEntity2 = new PersonEntity.PersonBuilder().username("jonas").email("jonas.peter@example.com").password("pass2")
                .firstName("Jonas").lastName("Peter").gender(Gender.MALE).dob(calendarToDate(1978, 9, 1)).createPerson();
        PersonEntity personEntity3 = new PersonEntity.PersonBuilder().username("zoe45").email("zoe.bennett@example.com").password("pass3")
                .firstName("Zoe").lastName("Bennett").gender(Gender.FEMALE).dob(calendarToDate(2005, 4, 21)).createPerson();
        PersonEntity personEntity4 = new PersonEntity.PersonBuilder().username("lottaro").email("lotta.aro@example.com").password("pass4")
                .firstName("Lotta").lastName("Aro").gender(Gender.FEMALE).dob(calendarToDate(2000, 1, 29)).createPerson();
        PersonEntity personEntity5 = new PersonEntity.PersonBuilder().username("sara1997").email("sara.brun@example.com").password("pass5")
                .firstName("Sara").lastName("Brun").gender(Gender.FEMALE).dob(calendarToDate(1997, 12, 18)).createPerson();

        personServiceLocal.persistPerson(personEntity1, Role.ADMIN);
        personServiceLocal.persistPerson(personEntity2, Role.SUPERVISOR);
        personServiceLocal.persistPerson(personEntity3, Role.USER);
        personServiceLocal.persistPerson(personEntity4, Role.USER);
        personServiceLocal.persistPerson(personEntity5, Role.USER);

        TeamEntity teamEntity1 = new TeamEntity("Manchester City");
        TeamEntity teamEntity2 = new TeamEntity("FC Barcelona");
        TeamEntity teamEntity3 = new TeamEntity("Paris Saint Germain");

        teamServiceLocal.persistTeam(teamEntity1, personEntity1);
        teamServiceLocal.persistTeam(teamEntity2, personEntity2);
        teamServiceLocal.persistTeam(teamEntity3, personEntity5);

        TournamentEntity tournamentEntity = new IndividualRoundRobinTournamentEntity(3, 0, 1, "Premier League", "English first class championship", 20, calendarToDate(2018, 2, 1));
        TournamentEntity ligue1 = new IndividualRoundRobinTournamentEntity(3, 0, 1, "Ligue 1", "French first class championship", 20, calendarToDate(2018, 2, 1));
        TournamentEntity teamtournamentEntity = new TeamRoundRobinTournamentEntity(3, 0, 1, "NB1", "Hungarian first class football championship", 10, calendarToDate(2018, 1, 19));
        tournamentEntity.setStatus(TournamentStatus.IN_PROGRESS);
        teamtournamentEntity.setStatus(TournamentStatus.IN_PROGRESS);
        tournamentServiceLocal.persistTournament(ligue1);
        tournamentServiceLocal.persistTournament(tournamentEntity);
        tournamentServiceLocal.persistTournament(teamtournamentEntity);
        
        IndividualEliminationTournamentEntity tmp = new IndividualEliminationTournamentEntity(2l, "UK OPEN", "Darts championship", 4, calendarToDate(2018, 2, 1));
        tournamentServiceLocal.persistTournament(tmp);
        
        IndividualEliminationTournamentEntity carabaoCup = new IndividualEliminationTournamentEntity(2l, "Carabao Cup 2018", "English football competition", 4, calendarToDate(2018, 2, 1));
        carabaoCup.setStatus(TournamentStatus.IN_PROGRESS);
        tournamentServiceLocal.persistTournament(carabaoCup);
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "geri");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "jonas");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "zoe45");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "lottaro");
        tournamentServiceLocal.kickoffElimination(carabaoCup.getId());

        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) tournamentEntity, personEntity1);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) tournamentEntity, personEntity2);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) tournamentEntity, personEntity4);

        IndividualRoundRobinStandingEntity standing1 = new IndividualRoundRobinStandingEntity(personEntity1, 0, 0, 0, 0, 0, tournamentEntity);
        IndividualRoundRobinStandingEntity standing2 = new IndividualRoundRobinStandingEntity(personEntity2, 0, 0, 0, 0, 0, tournamentEntity);
        IndividualRoundRobinStandingEntity standing4 = new IndividualRoundRobinStandingEntity(personEntity4, 0, 0, 0, 0, 0, tournamentEntity);
        standingServiceLocal.persistStanding(standing1);
        standingServiceLocal.persistStanding(standing2);
        standingServiceLocal.persistStanding(standing4);

        MatchEntity matchEntity1 = new IndividualMatchEntity(4, 3, personEntity1, personEntity2);
        MatchEntity matchEntity2 = new IndividualMatchEntity(0, 2, personEntity1, personEntity4);
        MatchEntity matchEntity3 = new IndividualMatchEntity(0, 2, personEntity2, personEntity4);
        matchEntity1.setStatus(MatchStatus.FINISHED);
        matchEntity2.setStatus(MatchStatus.SCHEDULED);
        matchEntity3.setStatus(MatchStatus.NEW);
        matchEntity1.setTime(Date.from(LocalDateTime.of(2018, Month.MARCH, 2, 20, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity2.setTime(Date.from(LocalDateTime.of(2018, Month.APRIL, 11, 8, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity3.setTime(Date.from(LocalDateTime.of(2018, Month.JUNE, 30, 10, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity1, standing1, standing2, tournamentEntity);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity2, standing1, standing4, tournamentEntity);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity3, standing2, standing4, tournamentEntity);

    }

    private Date calendarToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

}
