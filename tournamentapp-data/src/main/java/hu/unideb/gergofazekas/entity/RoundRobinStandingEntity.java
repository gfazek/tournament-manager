/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.entity;

import hu.unideb.gergofazekas.utility.DrawStrategy;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author gfazekas
 */
@MappedSuperclass
public abstract class RoundRobinStandingEntity extends StandingEntity implements Comparable<RoundRobinStandingEntity> {

    @Basic(optional = false)
    @Column(name = "PLAYED")
    private int played;

    @Basic(optional = false)
    @Column(name = "WON")
    private int won;

    @Basic(optional = false)
    @Column(name = "DRAWN")
    private int drawn;

    @Basic(optional = false)
    @Column(name = "LOST")
    private int lost;

    @Basic(optional = false)
    @Column(name = "POINTS")
    private int points;

    public RoundRobinStandingEntity() {
    }

    public RoundRobinStandingEntity(int played, int won, int drawn, int lost, int points, TournamentEntity tournamentEntity) {
        super(tournamentEntity);
        this.played = played;
        this.won = won;
        this.drawn = drawn;
        this.lost = lost;
        this.points = points;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(RoundRobinStandingEntity o) {
        RoundRobinTournamentEntity rrt = (RoundRobinTournamentEntity) getTournamentEntity();
        TreeMap<Integer, DrawStrategy> drawstrategies = new TreeMap<>(rrt.getDrawStrategy());
        if (this.points > o.points) {
            return -1;
        } else if (this.points < o.points) {
            return 1;
        } else {
            for (Map.Entry<Integer, DrawStrategy> drawstrategy : drawstrategies.entrySet()) {
                switch (drawstrategy.getValue()) {
                    case BATTLE:
                        break;
                    case SCORE:
                        break;
                    case WIN:
                        if (this.won > o.won) {
                            return -1;
                        } else if (this.won < o.won) {
                            return 1;
                        }
                        break;
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "RoundRobinStandingEntity{" + "tournamentEntity=" + getTournamentEntity().getName() + ", played=" + played + ", won=" + won + ", drawn=" + drawn + ", lost=" + lost + ", points=" + points + '}';
    }

}
