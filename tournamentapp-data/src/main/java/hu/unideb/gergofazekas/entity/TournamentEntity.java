/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.CompetitorType;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "TOURNAMENT")
public class TournamentEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "COMPETITOR_TYPE")
    @Enumerated(EnumType.STRING)
    private CompetitorType competitorType;
    
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private int winPoint;
    
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private int loosePoint;
    
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private int drawPoint;
    
     
    
}
