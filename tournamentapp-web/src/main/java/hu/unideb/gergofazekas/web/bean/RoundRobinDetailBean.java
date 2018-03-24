/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.bean;

import hu.unideb.gergofazekas.entity.IndividualMatchEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinStandingEntity;
import hu.unideb.gergofazekas.entity.IndividualRoundRobinTournamentEntity;
import hu.unideb.gergofazekas.entity.PersonEntity;
import hu.unideb.gergofazekas.entity.StandingEntity;
import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.MatchServiceLocal;
import hu.unideb.gergofazekas.service.StandingServiceLocal;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import hu.unideb.gergofazekas.utility.CompetitorType;
import hu.unideb.gergofazekas.utility.TournamentStatus;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
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
public class RoundRobinDetailBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(RoundRobinDetailBean.class);

    private TournamentEntity tournamentEntity;
    private List<IndividualMatchEntity> matches;
    private List<IndividualRoundRobinStandingEntity> standings;
    private IndividualMatchEntity selectedMatch;
    private List<PersonEntity> competitors;
    private Date matchTime;
    private Double homeScore, awayScore;
    private int registeredCompetitors;

    @EJB
    private TournamentServiceLocal tournamentServiceLocal;

    @EJB
    private MatchServiceLocal matchServiceLocal;

    @EJB
    private StandingServiceLocal standingServiceLocal;

    public RoundRobinDetailBean() {
    }

    @PostConstruct
    public void init() {
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        tournamentEntity = tournamentServiceLocal.findTournament(Long.parseLong(parameterMap.get("id")));
        standings = standingServiceLocal.findByTournament(tournamentEntity.getId()).stream()
                .map(e -> (IndividualRoundRobinStandingEntity) e)
                .collect(Collectors.toList());
//        standings.sort(Comparator.comparing(IndividualRoundRobinStandingEntity::getPoints).reversed());
          Collections.sort(standings);
        registeredCompetitors = fetchRegisteredCompetitors();
        if (tournamentEntity.getCompetitorType() == CompetitorType.PLAYER) {
            IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            competitors = irrt.getPeople();
            logger.debug("competitors are: {}", competitors);
        }
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
        return "tournaments?faces-redirect=true&successEntry=true";
    }

    public String deleteEntry() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        tournamentServiceLocal.deleteEntry(tournamentEntity.getId(), username);
        return "tournaments?faces-redirect=true&deleteEntry=true";
    }

    public void deleteEntry(String username) {
        tournamentServiceLocal.deleteEntry(tournamentEntity.getId(), username);
        competitors = tournamentServiceLocal.getIndividualCompetitors(tournamentEntity.getId(), tournamentEntity.getType());
        logger.debug("competitors after delete: {}", competitors);
    }

    public boolean showEntry() {
        if (tournamentEntity.getStatus() != TournamentStatus.OPEN) {
            return false;
        }
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
            for (PersonEntity personEntity : competitors) {
                if (personEntity.getUsername().equals(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void scheduleMatch() {
        matchServiceLocal.scheduleMatch(selectedMatch, matchTime);
    }

    public void registerResult() {
        logger.debug("registerResult() method got invoked.");
        logger.debug("SelectedMatch is: {}", selectedMatch);
        matchServiceLocal.registerRoundRobinMatchResult(selectedMatch, homeScore.intValue(), awayScore.intValue());
        List<StandingEntity> tmp = standingServiceLocal.findByTournament(tournamentEntity.getId());
        standings = tmp.stream()
                .map(e -> (IndividualRoundRobinStandingEntity) e)
                .collect(Collectors.toList());
        standings.sort(Comparator.comparing(IndividualRoundRobinStandingEntity::getPoints).reversed());
        logger.debug("Standings are refreshed: {}", getStandings());
    }

    public String kickoff() {
        tournamentServiceLocal.kickoffRoundRobin(tournamentEntity.getId());
        return "tournaments?faces-redirect=true&successKickoff=true";
    }

    public int fetchRegisteredCompetitors() {
        if (tournamentEntity.getCompetitorType() == CompetitorType.PLAYER) {
            IndividualRoundRobinTournamentEntity tmp = (IndividualRoundRobinTournamentEntity) tournamentEntity;
            return tmp.getPeople().size();
        }
        return -1;
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
        return standings;
    }

    public void setStandings(List<IndividualRoundRobinStandingEntity> standings) {
        this.standings = standings;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public MatchServiceLocal getMatchServiceLocal() {
        return matchServiceLocal;
    }

    public void setMatchServiceLocal(MatchServiceLocal matchServiceLocal) {
        this.matchServiceLocal = matchServiceLocal;
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

    public IndividualMatchEntity getSelectedMatch() {
        return selectedMatch;
    }

    public void setSelectedMatch(IndividualMatchEntity selectedMatch) {
        this.selectedMatch = selectedMatch;
    }

    public int getRegisteredCompetitors() {
        return registeredCompetitors;
    }

    public void setRegisteredCompetitors(int registeredCompetitors) {
        this.registeredCompetitors = registeredCompetitors;
    }

    public List<PersonEntity> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<PersonEntity> competitors) {
        this.competitors = competitors;
    }

    public StandingServiceLocal getStandingServiceLocal() {
        return standingServiceLocal;
    }

    public void setStandingServiceLocal(StandingServiceLocal standingServiceLocal) {
        this.standingServiceLocal = standingServiceLocal;
    }

}
