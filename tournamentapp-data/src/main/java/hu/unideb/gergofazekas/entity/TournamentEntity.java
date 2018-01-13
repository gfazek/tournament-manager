/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "TOURNAMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NamedQueries({
    @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM TournamentEntity t"),
    @NamedQuery(name = "Tournament.findOpens", query = "SELECT t FROM TournamentEntity t WHERE t.status = hu.unideb.gergofazekas.utility.TournamentStatus.OPEN")
})
public abstract class TournamentEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TournamentStatus status;
    
    @Basic(optional = false)
    @Column(name = "NOCOMPETITORS")
    private int numberOfCompetitors;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date start;
    
    @OneToMany
    private List<MatchEntity> matches;

    public TournamentEntity() {
    }

    public TournamentEntity(String name, String description, int numberOfCompetitors, Date start) {
        this.name = name;
        this.description = description;
        this.status = TournamentStatus.OPEN;
        this.numberOfCompetitors = numberOfCompetitors;
        this.start = start;
        this.matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MatchEntity> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public int getNumberOfCompetitors() {
        return numberOfCompetitors;
    }

    public void setNumberOfCompetitors(int numberOfCompetitors) {
        this.numberOfCompetitors = numberOfCompetitors;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    
}
