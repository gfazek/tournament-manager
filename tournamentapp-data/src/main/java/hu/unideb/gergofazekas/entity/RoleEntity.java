/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.Role;
import java.io.Serializable;
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
    private List<PersonEntity> persons;

    public RoleEntity() {
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }
    
}
