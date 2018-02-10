/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.MatchEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private List<IndividualMatchEntity> matches;
    private List<IndividualRoundRobinStandingEntity> standings;

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
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        tournamentServiceLocal.persistEntry(tournamentEntity.getId(), username);
        return "index";
    }

    public boolean showEntry() {
        logger.debug("TournamentEntity: {}", tournamentEntity);
        if (tournamentEntity.getStatus() != TournamentStatus.OPEN) {
            return false;
        }
        if (tournamentEntity instanceof IndividualRoundRobinTournamentEntity) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            List<PersonEntity> competitors = tmp.getPeople();
            logger.debug("CompetitorS: {}", competitors);
            Stream<PersonEntity> s = competitors.stream()
                    .filter(p -> p.getUsername().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
            logger.debug("It gets null value?: {}", s);
            Optional<PersonEntity> competitor = s.findFirst();
            return !competitor.isPresent();
//            Optional<PersonEntity> competitor = competitors.stream()
//                    .filter(p -> p.getUsername().equals(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()))
//                    .findFirst();
//            return !competitor.isPresent();
        }
        return true;
    }

    public void kickoff() {
        tournamentServiceLocal.kickoff(tournamentEntity.getId());
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }

    public List<IndividualMatchEntity> getMatches() {
        return tournamentEntity.getMatches()
                .stream()
                .map(e -> (IndividualMatchEntity) e)
                .collect(Collectors.toList());
    }

    public void setMatches(List<IndividualMatchEntity> matches) {
        this.matches = matches;
    }
    
     public List<IndividualRoundRobinStandingEntity> getStandings() {
        return tournamentEntity.getStandings()
                .stream()
                .map(e -> (IndividualRoundRobinStandingEntity) e)
                .collect(Collectors.toList());
    }

    public void setStandings(List<IndividualRoundRobinStandingEntity> standings) {
        this.standings = standings;
    }
    
}
