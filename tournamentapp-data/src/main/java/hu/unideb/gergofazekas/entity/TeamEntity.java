/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "TEAM")
public class TeamEntity extends BaseEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    
    @ManyToMany(mappedBy = "teams")
    private List<PersonEntity> players;
    
    public TeamEntity() {
    }

    public TeamEntity(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public List<PersonEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PersonEntity> players) {
        this.players = players;
    }
    
}
