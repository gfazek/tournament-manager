/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.DrawStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author gfazekas
 */
@Entity
@DiscriminatorValue(value = "IndividualRoundRobin")
@NamedQueries({
    @NamedQuery(name = "Standing.findIndividualRoundRobinOne", query = "SELECT s FROM IndividualRoundRobinStandingEntity s WHERE s.person.id = :pid AND s.tournamentEntity.id = :tid")
})
public class IndividualRoundRobinStandingEntity extends RoundRobinStandingEntity implements Comparable<IndividualRoundRobinStandingEntity> {

    @ManyToOne
    private PersonEntity person;

    public IndividualRoundRobinStandingEntity() {
    }

    public IndividualRoundRobinStandingEntity(PersonEntity person, int played, int won, int drawn, int lost, int points, TournamentEntity tournamentEntity) {
        super(played, won, drawn, lost, points, tournamentEntity);
        this.person = person;
    }

    public PersonEntity getPersonEntity() {
        return person;
    }

    public void setPersonEntity(PersonEntity person) {
        this.person = person;
    }

    @Override
    public int compareTo(IndividualRoundRobinStandingEntity o) {
        IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) getTournamentEntity();
        TreeMap<Integer, DrawStrategy> drawstrategy = new TreeMap<>(irrt.getDrawStrategy());
        List<IndividualMatchEntity> battleMatches = getBattleMatches(irrt.getMatches(), this, o);
        List<IndividualMatchEntity> thisMatches = getRelatedMatches(irrt.getMatches(), this);
        List<IndividualMatchEntity> oMatches = getRelatedMatches(irrt.getMatches(), o);
        int thisBattleScore = getBattleScore(battleMatches, this);
        int oBattleScore = getBattleScore(battleMatches, o);
        int thisGoalRatio = getGoalRatio(thisMatches, this);
        int oGoalRatio = getGoalRatio(oMatches, o);
        if (this.getPoints() > o.getPoints()) {
            return -1;
        } else if (this.getPoints() < o.getPoints()) {
            return 1;
        } else {
            for (Map.Entry<Integer, DrawStrategy> e : drawstrategy.entrySet()) {
                switch (e.getValue()) {
                    case BATTLE:
                        if (thisBattleScore > oBattleScore) {
                            return -1;
                        } else if (thisBattleScore < oBattleScore) {
                            return 1;
                        }
                        break;
                    case SCORE:
                        if (thisGoalRatio > oGoalRatio) {
                            return -1;
                        } else if (thisGoalRatio < oGoalRatio) {
                            return 1;
                        }
                        break;
                    case WIN:
                        if (this.getWon() > o.getWon()) {
                            return -1;
                        } else if (this.getWon() < o.getWon()) {
                            return 1;
                        }
                        break;
                }
            }
        }
        return 0;
    }

    private List<IndividualMatchEntity> getRelatedMatches(List<MatchEntity> matches, IndividualRoundRobinStandingEntity standing) {
        List<IndividualMatchEntity> relatedMatches = new ArrayList<>();
        for (MatchEntity match : matches) {
            IndividualMatchEntity individualMatch = (IndividualMatchEntity) match;
            if (Objects.equals(individualMatch.getHomeCompetitor().getId(), standing.getPersonEntity().getId())
                    || Objects.equals(individualMatch.getAwayCompetitor().getId(), standing.getPersonEntity().getId())) {
                relatedMatches.add(individualMatch);
            }
        }
        return relatedMatches;
    }

    private List<IndividualMatchEntity> getBattleMatches(List<MatchEntity> matches, IndividualRoundRobinStandingEntity standing1, IndividualRoundRobinStandingEntity standing2) {
        List<IndividualMatchEntity> battleMatches = new ArrayList<>();
        for (MatchEntity match : matches) {
            IndividualMatchEntity individualMatch = (IndividualMatchEntity) match;
            if ((Objects.equals(individualMatch.getHomeCompetitor().getId(), standing1.getPersonEntity().getId()) && Objects.equals(individualMatch.getAwayCompetitor().getId(), standing2.getPersonEntity().getId()))
                    || (Objects.equals(individualMatch.getAwayCompetitor().getId(), standing1.getPersonEntity().getId()) && Objects.equals(individualMatch.getHomeCompetitor().getId(), standing2.getPersonEntity().getId()))) {
                battleMatches.add(individualMatch);
            }
        }
        return battleMatches;
    }

    private int getGoalRatio(List<IndividualMatchEntity> relatedMatches, IndividualRoundRobinStandingEntity standing) {
        int goalRatio = 0;
        for (IndividualMatchEntity match : relatedMatches) {
            if (Objects.equals(match.getHomeCompetitor().getId(), standing.getPersonEntity().getId())) {
                goalRatio += match.getHomeScore() - match.getAwayScore();
            } else {
                goalRatio += match.getAwayScore() - match.getHomeScore();
            }
        }
        return goalRatio;
    }

    private int getBattleScore(List<IndividualMatchEntity> battleMatches, IndividualRoundRobinStandingEntity standing) {
        IndividualRoundRobinTournamentEntity irrt = (IndividualRoundRobinTournamentEntity) getTournamentEntity();
        int battleScore = 0;
        for (IndividualMatchEntity match : battleMatches) {
            if (Objects.equals(standing.getPersonEntity().getId(), match.getHomeCompetitor().getId())) {
                if (match.getHomeScore() > match.getAwayScore()) {
                    battleScore += irrt.getWinPoint();
                } else if (match.getHomeScore() < match.getAwayScore()) {
                    battleScore += irrt.getLoosePoint();
                }
            } else {
                if (match.getHomeScore() > match.getAwayScore()) {
                    battleScore += irrt.getLoosePoint();
                } else if (match.getHomeScore() < match.getAwayScore()) {
                    battleScore += irrt.getWinPoint();
                }
            }
        }
        return battleScore;
    }

    @Override
    public String toString() {
        return "IndividualRoundRobinStandingEntity{" + "tournamentEntity=" + getTournamentEntity().getName() + ", played=" + getPlayed() + ", won=" + getWon() + ", drawn=" + getDrawn() + ", lost=" + getLost() + ", points=" + getPoints() + ", person=" + person.getUsername() + '}';
    }

}
