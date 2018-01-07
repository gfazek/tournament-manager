/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.Gender;
import hu.unideb.gergofazekas.utility.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "Person.findPersonByUsername", query = "SELECT p FROM PersonEntity p WHERE p.username LIKE :username"),
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM PersonEntity p")
})
public class PersonEntity extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @Column(name = "ENABLED")
    private boolean enabled = true;
    @Basic(optional = false)
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LASTNAME")
    private String lastName;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dob;
    
    @OneToMany(mappedBy = "homeCompetitor")
    private List<IndividualMatchEntity> homeMatches;
    @OneToMany(mappedBy = "awayCompetitor")
    private List<IndividualMatchEntity> awayMatches;

    @JoinTable(name = "PERSON_ROLE", joinColumns = {
        @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<RoleEntity> roles;

    @JoinTable(name = "PERSON_TEAM", joinColumns = {
        @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")})
    @ManyToMany
    @JoinColumn(name = "TEAM_ID")
    private List<TeamEntity> teams;
    
    @ManyToMany(mappedBy = "people")
    private List<IndividualRoundRobinTournamentEntity> roundRobinTournaments;
    

    public PersonEntity() {
        this.roles = new ArrayList<>();
    }

    public PersonEntity(String username, String email, String password, String firstName, String lastName, Gender gender, Date dob) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.roles = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.roundRobinTournaments = new ArrayList<>();
    }

    public static class PersonBuilder {

        private String nestedUsername;
        private String nestedEmail;
        private String nestedPassword;
        private String nestedFirstName;
        private String nestedLastName;
        private Gender nestedGender;
        private Date nestedDob;

        public PersonBuilder username(String username) {
            this.nestedUsername = username;
            return this;
        }

        public PersonBuilder email(String email) {
            this.nestedEmail = email;
            return this;
        }

        public PersonBuilder password(String password) {
            this.nestedPassword = password;
            return this;
        }

        public PersonBuilder firstName(String firstName) {
            this.nestedFirstName = firstName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            this.nestedLastName = lastName;
            return this;
        }

        public PersonBuilder gender(Gender gender) {
            this.nestedGender = gender;
            return this;
        }

        public PersonBuilder dob(Date dob) {
            this.nestedDob = dob;
            return this;
        }
        
        public PersonEntity createPerson() {
            return new PersonEntity(nestedUsername, nestedEmail, nestedPassword, nestedFirstName, nestedLastName, nestedGender, nestedDob);
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }

    public List<IndividualRoundRobinTournamentEntity> getRoundRobinTournaments() {
        return roundRobinTournaments;
    }

    public void setRoundRobinTournaments(List<IndividualRoundRobinTournamentEntity> roundRobinTournaments) {
        this.roundRobinTournaments = roundRobinTournaments;
    }

    public List<IndividualMatchEntity> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<IndividualMatchEntity> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<IndividualMatchEntity> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<IndividualMatchEntity> awayMatches) {
        this.awayMatches = awayMatches;
    }
    
}
