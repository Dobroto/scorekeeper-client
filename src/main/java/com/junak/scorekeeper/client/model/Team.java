package com.junak.scorekeeper.client.model;

import java.util.List;

public class Team {
    private int id;

    private String teamNameLong;

    private String teamNameShort;

    private Boolean isAttacking;

    private List<Integer> players;

    private List<Integer> homeGames;

    private List<Integer> visitorGames;

    public Team() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamNameLong() {
        return teamNameLong;
    }

    public void setTeamNameLong(String teamNameLong) {
        this.teamNameLong = teamNameLong;
    }

    public String getTeamNameShort() {
        return teamNameShort;
    }

    public void setTeamNameShort(String teamNameShort) {
        this.teamNameShort = teamNameShort;
    }

    public Boolean getAttacking() {
        return isAttacking;
    }

    public void setAttacking(Boolean attacking) {
        isAttacking = attacking;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(List<Integer> players) {
        this.players = players;
    }

    public List<Integer> getHomeGames() {
        return homeGames;
    }

    public void setHomeGames(List<Integer> homeGames) {
        this.homeGames = homeGames;
    }

    public List<Integer> getVisitorGames() {
        return visitorGames;
    }

    public void setVisitorGames(List<Integer> visitorGames) {
        this.visitorGames = visitorGames;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamNameLong='" + teamNameLong + '\'' +
                ", teamNameShort='" + teamNameShort + '\'' +
                ", players=" + players +
                ", homeGames=" + homeGames +
                ", visitorGames=" + visitorGames +
                '}';
    }
}
