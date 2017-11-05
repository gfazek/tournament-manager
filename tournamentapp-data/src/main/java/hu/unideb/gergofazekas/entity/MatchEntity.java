/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "MATCH")
public class MatchEntity extends BaseEntity implements Serializable{
    
    @Basic(optional = false)
    @Column(name = "HOME_SCORE")
    private int homeScore;
    
    @Basic(optional = false)
    @Column(name = "AWAY_SCORE")
    private int awayScore;
    
}
