/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.RoleEntity;
import hu.unideb.gergofazekas.entity.TeamEntity;
import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.Role;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author gfazekas
 */
@Startup
@Singleton
public class InitializerBean {

    private static final Logger logger
            = Logger.getLogger("service.InitializerBean");
    
    @EJB
    private RoleBean roleBean;
    
//    @EJB
//    private PersonServiceLocal personBean;
//    
    @EJB
    private TeamBean teamBean;

    @PostConstruct
    public void init() {
        
        RoleEntity userRole = new RoleEntity(Role.USER);
        RoleEntity adminRole = new RoleEntity(Role.ADMIN);
        roleBean.persistRole(userRole);
        roleBean.persistRole(adminRole);
        PersonEntity personEntity1 = new PersonEntity.PersonBuilder().username("geri").email("romain.hoogmoed@example.com").password("pass")
                .firstName("Romain").lastName("Hoogmoed").gender(Gender.MALE).dob(calendarToDate(1981, 5, 9)).createPerson();
        PersonEntity personEntity2 = new PersonEntity.PersonBuilder().username("jonas").email("jonas.peter@example.com").password("pass2")
                .firstName("Jonas").lastName("Peter").gender(Gender.MALE).dob(calendarToDate(1978, 9, 1)).createPerson();
        PersonEntity personEntity3 = new PersonEntity.PersonBuilder().username("zoe45").email("zoe.bennett@example.com").password("pass3")
                .firstName("Zoe").lastName("Bennett").gender(Gender.FEMALE).dob(calendarToDate(2005, 4, 21)).createPerson();
        PersonEntity personEntity4 = new PersonEntity.PersonBuilder().username("lottaro").email("lotta.aro@example.com\"").password("pass3")
                .firstName("Lotta").lastName("Aro").gender(Gender.FEMALE).dob(calendarToDate(2000, 1, 29)).createPerson();
        PersonEntity personEntity5 = new PersonEntity.PersonBuilder().username("sara1997").email("sara.brun@example.com").password("pass4")
                .firstName("Sara").lastName("Brun").gender(Gender.FEMALE).dob(calendarToDate(1997, 12, 18)).createPerson();
        
        roleBean.persistPerson(personEntity1, userRole);
        roleBean.persistPerson(personEntity2, userRole);
        roleBean.persistPerson(personEntity3, userRole);
        roleBean.persistPerson(personEntity4, userRole);
        roleBean.persistPerson(personEntity5, userRole);
        
        TeamEntity teamEntity1 = new TeamEntity("Manchester City");
        TeamEntity teamEntity2 = new TeamEntity("FC Barcelona");
        TeamEntity teamEntity3 = new TeamEntity("Paris Saint Germain");
        
        teamBean.persistTeam(teamEntity1, personEntity1);
        teamBean.persistTeam(teamEntity1, personEntity2);
        teamBean.persistTeam(teamEntity3, personEntity5);
    }

    private Date calendarToDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

}
