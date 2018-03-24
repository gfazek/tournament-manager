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
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.utility.DrawStrategy;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.MatchStatus;
import hu.unideb.gergofazekas.utility.Role;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;
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
    private TournamentServiceLocal tournamentServiceLocal;

    @EJB
    private MatchServiceLocal matchServiceLocal;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    @PostConstruct
    public void init() {
        logger.info("Persisting initial objects to the database...");
        initRoles();
        PersonEntity[] people = initPeople();
        initPremierLeague(people);
        initCarabaoCup();
        initUkOpen();
        initLigue1();
    }

    private Date calendarToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private void initRoles() {
        logger.info("Initializing roles...");
        RoleEntity userRole = new RoleEntity(Role.USER);
        RoleEntity adminRole = new RoleEntity(Role.ADMIN);
        RoleEntity supervisorRole = new RoleEntity(Role.SUPERVISOR);
        roleServiceLocal.persistRole(userRole);
        roleServiceLocal.persistRole(adminRole);
        roleServiceLocal.persistRole(supervisorRole);
    }

    public PersonEntity[] initPeople() {
        logger.info("Initializing people...");
        PersonEntity personEntity1 = new PersonEntity.PersonBuilder().username("geri").email("geri@example.com").password("pass")
                .firstName("Geri").lastName("Fazekas").gender(Gender.MALE).dob(calendarToDate(1995, 5, 9)).createPerson();
        PersonEntity personEntity2 = new PersonEntity.PersonBuilder().username("jonas").email("jonas.peter@example.com").password("pass2")
                .firstName("Jonas").lastName("Peter").gender(Gender.MALE).dob(calendarToDate(1978, 9, 1)).createPerson();
        PersonEntity personEntity3 = new PersonEntity.PersonBuilder().username("zoe45").email("zoe.bennett@example.com").password("pass3")
                .firstName("Zoe").lastName("Bennett").gender(Gender.FEMALE).dob(calendarToDate(2005, 4, 21)).createPerson();
        PersonEntity personEntity4 = new PersonEntity.PersonBuilder().username("lottaro").email("lotta.aro@example.com").password("pass4")
                .firstName("Lotta").lastName("Aro").gender(Gender.FEMALE).dob(calendarToDate(2000, 1, 29)).createPerson();
        PersonEntity personEntity5 = new PersonEntity.PersonBuilder().username("sara1997").email("sara.brun@example.com").password("pass5")
                .firstName("Sara").lastName("Brun").gender(Gender.FEMALE).dob(calendarToDate(1997, 12, 18)).createPerson();
        PersonEntity personEntity6 = new PersonEntity.PersonBuilder().username("davidsilva").email("david.silva@example.com").password("pass6")
                .firstName("David").lastName("Silva").gender(Gender.MALE).dob(calendarToDate(1986, 3, 15)).createPerson();
        PersonEntity personEntity7 = new PersonEntity.PersonBuilder().username("neymarjr").email("neymar@example.com").password("pass7")
                .firstName("Neymar").lastName("Jr.").gender(Gender.MALE).dob(calendarToDate(1992, 2, 9)).createPerson();
        PersonEntity personEntity8 = new PersonEntity.PersonBuilder().username("becky").email("becky.carter80@example.com").password("pass8")
                .firstName("Becky").lastName("Carter").gender(Gender.FEMALE).dob(calendarToDate(1983, 7, 1)).createPerson();
        PersonEntity personEntity9 = new PersonEntity.PersonBuilder().username("rebecca91").email("rebecca1991@example.com").password("pass9")
                .firstName("Rebecca").lastName("Henry").gender(Gender.FEMALE).dob(calendarToDate(1991, 11, 30)).createPerson();
        PersonEntity personEntity10 = new PersonEntity.PersonBuilder().username("austin").email("austinjacobs0@example.com").password("pass10")
                .firstName("Austin").lastName("Jacobs").gender(Gender.MALE).dob(calendarToDate(1988, 2, 6)).createPerson();

        personServiceLocal.persistPerson(personEntity1, Role.ADMIN);
        personServiceLocal.persistPerson(personEntity2, Role.SUPERVISOR);
        personServiceLocal.persistPerson(personEntity3, Role.USER);
        personServiceLocal.persistPerson(personEntity4, Role.USER);
        personServiceLocal.persistPerson(personEntity5, Role.USER);
        personServiceLocal.persistPerson(personEntity6, Role.USER);
        personServiceLocal.persistPerson(personEntity7, Role.USER);
        personServiceLocal.persistPerson(personEntity8, Role.USER);
        personServiceLocal.persistPerson(personEntity9, Role.USER);
        personServiceLocal.persistPerson(personEntity10, Role.USER);

        return new PersonEntity[]{personEntity1, personEntity2, personEntity3, personEntity4, personEntity5,
            personEntity6, personEntity7, personEntity8, personEntity9, personEntity10};
    }

    private void initCarabaoCup() {
        logger.info("Initializing Carabao Cup...");
        IndividualEliminationTournamentEntity carabaoCup = new IndividualEliminationTournamentEntity(3l, "Carabao Cup 2018", "English football competition", 8, calendarToDate(2018, 2, 1));
        carabaoCup.setStatus(TournamentStatus.IN_PROGRESS);
        tournamentServiceLocal.persistTournament(carabaoCup);
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "geri");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "jonas");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "zoe45");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "lottaro");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "sara1997");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "davidsilva");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "neymarjr");
        tournamentServiceLocal.persistEntry(carabaoCup.getId(), "becky");
        tournamentServiceLocal.kickoffElimination(carabaoCup.getId());
    }

    private void initUkOpen() {
        logger.info("Initializing UKOpen...");
        IndividualEliminationTournamentEntity ukOpen = new IndividualEliminationTournamentEntity(2l, "UK OPEN", "Darts championship", 4, calendarToDate(2018, 2, 1));
        tournamentServiceLocal.persistTournament(ukOpen);
    }

    private void initLigue1() {
        logger.info("Initializing Ligue1...");
        TreeMap<Integer, DrawStrategy> drawstrategy = new TreeMap<>();
        drawstrategy.put(1, DrawStrategy.WIN);
        drawstrategy.put(3, DrawStrategy.BATTLE);
        drawstrategy.put(2, DrawStrategy.SCORE);
        TournamentEntity ligue1 = new IndividualRoundRobinTournamentEntity(3, 0, 1, "Ligue 1", "French first class championship", 20, calendarToDate(2018, 2, 1), drawstrategy);
        tournamentServiceLocal.persistTournament(ligue1);
    }

    private void initPremierLeague(PersonEntity[] people) {
        logger.info("Initializing Premier League...");
        TreeMap<Integer, DrawStrategy> drawstrategy = new TreeMap<>();
        drawstrategy.put(2, DrawStrategy.WIN);
        drawstrategy.put(1, DrawStrategy.BATTLE);
        drawstrategy.put(3, DrawStrategy.SCORE);
        IndividualRoundRobinTournamentEntity premierLeague = new IndividualRoundRobinTournamentEntity(3, 0, 1, "Premier League", "English first class championship", 5, calendarToDate(2018, 2, 1), drawstrategy);
        premierLeague.setStatus(TournamentStatus.CLOSED);
        tournamentServiceLocal.persistTournament(premierLeague);

        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) premierLeague, people[0]);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) premierLeague, people[1]);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) premierLeague, people[2]);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) premierLeague, people[3]);
        tournamentServiceLocal.persistCompetitor((IndividualRoundRobinTournamentEntity) premierLeague, people[4]);

        IndividualRoundRobinStandingEntity standing1 = new IndividualRoundRobinStandingEntity(people[0], 0, 0, 0, 0, 0, premierLeague);
        IndividualRoundRobinStandingEntity standing2 = new IndividualRoundRobinStandingEntity(people[1], 0, 0, 0, 0, 0, premierLeague);
        IndividualRoundRobinStandingEntity standing3 = new IndividualRoundRobinStandingEntity(people[2], 0, 0, 0, 0, 0, premierLeague);
        IndividualRoundRobinStandingEntity standing4 = new IndividualRoundRobinStandingEntity(people[3], 0, 0, 0, 0, 0, premierLeague);
        IndividualRoundRobinStandingEntity standing5 = new IndividualRoundRobinStandingEntity(people[4], 0, 0, 0, 0, 0, premierLeague);
        standingServiceLocal.persistStanding(standing1);
        standingServiceLocal.persistStanding(standing2);
        standingServiceLocal.persistStanding(standing3);
        standingServiceLocal.persistStanding(standing4);
        standingServiceLocal.persistStanding(standing5);

        MatchEntity matchEntity1 = new IndividualMatchEntity(4, 3, people[0], people[1]);
        MatchEntity matchEntity2 = new IndividualMatchEntity(1, 2, people[0], people[2]);
        MatchEntity matchEntity3 = new IndividualMatchEntity(3, 2, people[0], people[3]);
        MatchEntity matchEntity4 = new IndividualMatchEntity(0, 0, people[0], people[4]);
        MatchEntity matchEntity5 = new IndividualMatchEntity(1, 2, people[1], people[2]);
        MatchEntity matchEntity6 = new IndividualMatchEntity(2, 0, people[1], people[3]);
        MatchEntity matchEntity7 = new IndividualMatchEntity(0, 4, people[1], people[4]);
        MatchEntity matchEntity8 = new IndividualMatchEntity(1, 1, people[2], people[3]);
        MatchEntity matchEntity9 = new IndividualMatchEntity(3, 0, people[2], people[4]);
        MatchEntity matchEntity10 = new IndividualMatchEntity(3, 3, people[3], people[4]);

        matchEntity1.setStatus(MatchStatus.FINISHED);
        matchEntity2.setStatus(MatchStatus.FINISHED);
        matchEntity3.setStatus(MatchStatus.FINISHED);
        matchEntity4.setStatus(MatchStatus.FINISHED);
        matchEntity5.setStatus(MatchStatus.FINISHED);
        matchEntity6.setStatus(MatchStatus.FINISHED);
        matchEntity7.setStatus(MatchStatus.FINISHED);
        matchEntity8.setStatus(MatchStatus.FINISHED);
        matchEntity9.setStatus(MatchStatus.FINISHED);
        matchEntity10.setStatus(MatchStatus.FINISHED);
        
        matchEntity1.setTime(Date.from(LocalDateTime.of(2018, Month.MARCH, 2, 20, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity2.setTime(Date.from(LocalDateTime.of(2018, Month.APRIL, 11, 8, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity3.setTime(Date.from(LocalDateTime.of(2018, Month.JUNE, 30, 10, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity4.setTime(Date.from(LocalDateTime.of(2018, Month.MARCH, 2, 20, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity5.setTime(Date.from(LocalDateTime.of(2018, Month.APRIL, 11, 8, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity6.setTime(Date.from(LocalDateTime.of(2018, Month.JUNE, 30, 10, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity7.setTime(Date.from(LocalDateTime.of(2018, Month.MARCH, 2, 20, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity8.setTime(Date.from(LocalDateTime.of(2018, Month.APRIL, 11, 8, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity9.setTime(Date.from(LocalDateTime.of(2018, Month.JUNE, 30, 10, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchEntity10.setTime(Date.from(LocalDateTime.of(2018, Month.JUNE, 30, 10, 45).atZone(ZoneId.systemDefault()).toInstant()));
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity1, standing1, standing2, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity2, standing1, standing3, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity3, standing1, standing4, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity4, standing1, standing5, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity5, standing2, standing3, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity6, standing2, standing4, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity7, standing2, standing5, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity8, standing3, standing4, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity9, standing3, standing5, premierLeague);
        matchServiceLocal.persistMatch((IndividualMatchEntity) matchEntity10, standing4, standing5, premierLeague);

    }

}
