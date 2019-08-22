package com.junak.scorekeeper.client.model;

public class GamePitchingDetails {
    private int id;

    private int wins;

    private int loses;

    private int earnedRuns;

    private double inningsPitched;

    private double earnedRunAverage;

    private int saves;

    private int saveOpportunities;

    private int hits;

    private int runs;

    private int homeRuns;

    private int basesOnBalls;

    private int strikeOuts;

    private double average;

    private double whips;

    private Player player;

    private Game game;

    public GamePitchingDetails() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getEarnedRuns() {
        return earnedRuns;
    }

    public void setEarnedRuns(int earnedRuns) {
        this.earnedRuns = earnedRuns;
    }

    public double getInningsPitched() {
        return inningsPitched;
    }

    public void setInningsPitched(double inningsPitched) {
        this.inningsPitched = inningsPitched;
    }

    public double getEarnedRunAverage() {
        return earnedRunAverage;
    }

    public void setEarnedRunAverage(double earnedRunAverage) {
        this.earnedRunAverage = earnedRunAverage;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getSaveOpportunities() {
        return saveOpportunities;
    }

    public void setSaveOpportunities(int saveOpportunities) {
        this.saveOpportunities = saveOpportunities;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getHomeRuns() {
        return homeRuns;
    }

    public void setHomeRuns(int homeRuns) {
        this.homeRuns = homeRuns;
    }

    public int getBasesOnBalls() {
        return basesOnBalls;
    }

    public void setBasesOnBalls(int basesOnBalls) {
        this.basesOnBalls = basesOnBalls;
    }

    public int getStrikeOuts() {
        return strikeOuts;
    }

    public void setStrikeOuts(int strikeOuts) {
        this.strikeOuts = strikeOuts;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getWhips() {
        return whips;
    }

    public void setWhips(double whips) {
        this.whips = whips;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "GamePitchingDetails{" +
                "id=" + id +
                ", wins=" + wins +
                ", loses=" + loses +
                ", earnedRuns=" + earnedRuns +
                ", inningsPitched=" + inningsPitched +
                ", earnedRunAverage=" + earnedRunAverage +
                ", saves=" + saves +
                ", saveOpportunities=" + saveOpportunities +
                ", hits=" + hits +
                ", runs=" + runs +
                ", homeRuns=" + homeRuns +
                ", basesOnBalls=" + basesOnBalls +
                ", strikeOuts=" + strikeOuts +
                ", average=" + average +
                ", whips=" + whips +
                ", player=" + player +
                ", game=" + game +
                '}';
    }
}
