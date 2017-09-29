
import hu.unideb.gergofazekas.PersonService;
import hu.unideb.gergofazekas.PersonVO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class IndexBean implements Serializable {
    
    private String name;
    
    @EJB
    PersonService personService;

    public IndexBean() {
    }
    
    public IndexBean(String name) {
        this.name = name;
    }

    public void adding() {
        personService.addPerson(new PersonVO(name));
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
