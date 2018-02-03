/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.component.outputlabel.OutputLabel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author gfazekas
 */
@Named
@ViewScoped
public class TournamentDetailBean implements Serializable {
    
    private static final Logger logger = LogManager.getLogger(TournamentDetailBean.class);

    private TournamentEntity tournamentEntity;
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    public TournamentDetailBean() {
    }

    public TournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(TournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }
    
    public String submitEntry() {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        tournamentServiceLocal.persistEntry(tournamentEntity.getId(), username);
        return "index";
    }
    
    public boolean showEntry() {
        if (tournamentEntity.getStatus() != TournamentStatus.OPEN) {
            return false;
        } 
        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            List<PersonEntity> competitors = tournamentServiceLocal.getIndividualCompetitors(tournamentEntity.getId());
            Optional<PersonEntity> competitor = competitors.stream()
                    .filter(p -> p.getUsername().equals(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()))
                    .findFirst();
            return !competitor.isPresent();
        }
        return true;
    }
    
}
