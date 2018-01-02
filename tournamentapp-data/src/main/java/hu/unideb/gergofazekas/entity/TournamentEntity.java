/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
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
public abstract class TournamentEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "NOCOMPETITORS")
    private int numberOfCompetitors;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date start;
    
    @OneToMany(mappedBy = "tournament")
    private List<MatchEntity> matches;

    public TournamentEntity() {
    }

    public TournamentEntity(String name, int numberOfCompetitors, Date start) {
        this.name = name;
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
    
}
