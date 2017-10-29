/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.service;

import hu.unideb.gergofazekas.vo.PersonVo;
import javax.ejb.Local;

/**
 *
 * @author gfazekas
 */
@Local
public interface PersonServiceLocal {
    
    PersonVo createPerson(PersonVo personVo);
    PersonVo getPersonByUsername(String username);
    
}
