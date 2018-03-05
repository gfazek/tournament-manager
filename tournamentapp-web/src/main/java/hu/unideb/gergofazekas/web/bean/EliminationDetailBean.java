/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.EliminationMatchEntity;
import hu.unideb.gergofazekas.entity.EliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualEliminationTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.service.MatchServiceLocal;
import hu.unideb.gergofazekas.service.PersonServiceLocal;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author gfazekas
 */
@Named
@ViewScoped
public class EliminationDetailBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(EliminationDetailBean.class);

    private EliminationTournamentEntity tournamentEntity;
    private List<IndividualEliminationMatchEntity> matches;
    private EliminationMatchEntity selectedMatch;
    private Double homeScore, awayScore;
    private Date matchTime;
    private int registeredCompetitors;

    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    @EJB
    private MatchServiceLocal matchServiceLocal;
    
    @EJB
    private PersonServiceLocal personServiceLocal;

    public EliminationDetailBean() {
    }

    @PostConstruct
    public void init() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        tournamentEntity = (EliminationTournamentEntity) tournamentServiceLocal.findTournament(Long.parseLong(parameterMap.get("id")));
        registeredCompetitors = fetchRegisteredCompetitors();
    }

    public boolean showEntry() {
        if (tournamentEntity.getStatus() != TournamentStatus.OPEN) {
            return false;
        }
        IndividualEliminationTournamentEntity tmp = (IndividualEliminationTournamentEntity) tournamentEntity;
        List<PersonEntity> competitors = tmp.getPeople();
        Stream<PersonEntity> s = competitors.stream()
                .filter(p -> p.getUsername().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
        Optional<PersonEntity> competitor = s.findFirst();
        return !competitor.isPresent();
    }
    
    public boolean showDeleteEntry() {
        if (tournamentEntity.getStatus() != TournamentStatus.OPEN) {
            return false;
        }
        if (tournamentEntity.getCompetitorType() == CompetitorType.PLAYER) {
            IndividualEliminationTournamentEntity tmp = (IndividualEliminationTournamentEntity) tournamentEntity;
            for (PersonEntity personEntity : tmp.getPeople()) {
                if (personEntity.getUsername().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String submitEntry() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        tournamentServiceLocal.persistEntry(tournamentEntity.getId(), username);
        return "tournaments?faces-redirect=true&successEntry=true";
    }
    
    public String deleteEntry() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        tournamentServiceLocal.deleteEntry(tournamentEntity.getId(), username);
        return "tournaments?faces-redirect=true&deleteEntry=true";
    }
    
    public void deleteEntry(String username) {
        tournamentServiceLocal.deleteEntry(tournamentEntity.getId(), username);
    }

    public String kickoff() {
        tournamentServiceLocal.kickoffElimination(tournamentEntity.getId());
        return "tournaments?faces-redirect=true&successKickoff=true";
    }

    public void registerResult() {
        matchServiceLocal.registerEliminationMatchResult(selectedMatch, homeScore.intValue(), awayScore.intValue());
    }

    public void scheduleMatch() {
        matchServiceLocal.scheduleMatch(selectedMatch, matchTime);
    }

    public int fetchRegisteredCompetitors() {
        if (tournamentEntity.getCompetitorType() == CompetitorType.PLAYER) {
            IndividualEliminationTournamentEntity tmp = (IndividualEliminationTournamentEntity) tournamentEntity;
            return tmp.getPeople().size();
        }
        return -1;
    }
    
    public List<IndividualEliminationMatchEntity> getMatches() {
        return tournamentEntity.getMatches()
                .stream()
                .map(e -> (IndividualEliminationMatchEntity) e)
                .collect(Collectors.toList());
    }

    public EliminationTournamentEntity getTournamentEntity() {
        return tournamentEntity;
    }

    public void setTournamentEntity(EliminationTournamentEntity tournamentEntity) {
        this.tournamentEntity = tournamentEntity;
    }

    public Double getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Double homeScore) {
        this.homeScore = homeScore;
    }

    public Double getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Double awayScore) {
        this.awayScore = awayScore;
    }

    public EliminationMatchEntity getSelectedMatch() {
        return selectedMatch;
    }

    public void setSelectedMatch(EliminationMatchEntity selectedMatch) {
        this.selectedMatch = selectedMatch;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public void setRegisteredCompetitors(int registeredCompetitors) {
        this.registeredCompetitors = registeredCompetitors;
    }
    
    public int getRegisteredCompetitors() {
        return registeredCompetitors;
    }

    public TournamentServiceLocal getTournamentServiceLocal() {
        return tournamentServiceLocal;
    }

    public void setTournamentServiceLocal(TournamentServiceLocal tournamentServiceLocal) {
        this.tournamentServiceLocal = tournamentServiceLocal;
    }

}
