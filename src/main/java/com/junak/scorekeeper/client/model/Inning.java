package com.junak.scorekeeper.client.model;

public class Inning {
    private int id;

    private Game game;

    private int inningNumber;

    private int visitorTeamRuns;

    private int homeTeamRuns;

    private int currentOuts;

    public Inning() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(int inningNumber) {
        this.inningNumber = inningNumber;
    }

    public int getVisitorTeamRuns() {
        return visitorTeamRuns;
    }

    public void setVisitorTeamRuns(int visitorTeamRuns) {
        this.visitorTeamRuns = visitorTeamRuns;
    }

    public int getHomeTeamRuns() {
        return homeTeamRuns;
    }

    public void setHomeTeamRuns(int homeTeamRuns) {
        this.homeTeamRuns = homeTeamRuns;
    }

    public int getCurrentOuts() {
        return currentOuts;
    }

    public void setCurrentOuts(int currentOuts) {
        this.currentOuts = currentOuts;
    }

    @Override
    public String toString() {
        return "Inning{" +
                "id=" + id +
                ", game=" + game +
                ", inningNumber=" + inningNumber +
                ", visitorTeamRuns=" + visitorTeamRuns +
                ", homeTeamRuns=" + homeTeamRuns +
                ", currentOuts=" + currentOuts +
                '}';
    }
}

