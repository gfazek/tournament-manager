/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas;

import java.io.Serializable;

/**
 *
 * @author gfazekas
 */
public class PersonVO implements Serializable{
    
    private String name;

    public PersonVO() {
    }

    public PersonVO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
