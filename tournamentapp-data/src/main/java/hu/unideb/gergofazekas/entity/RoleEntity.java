/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author gfazekas
 */
@Entity
@Table(name = "ROLE")
public class RoleEntity extends BaseEntity implements Serializable {
    
    @Basic(optional = false)
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @ManyToMany(mappedBy = "roles")
    private List<PersonEntity> people;

    public RoleEntity() {
        this.people = new ArrayList<>();
    }
    
    public RoleEntity(Role role) {
        this.role = role;
        this.people = new ArrayList<>();
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PersonEntity> getPeople() {
        return people;
    }

    public void setPeople(List<PersonEntity> people) {
        this.people = people;
    }
    
}
