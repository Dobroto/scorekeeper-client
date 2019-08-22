package com.junak.scorekeeper.client.model;

public class GameHittingDetails {
    private int id;

    private int plateAppearences;

    private int sacrificeHits;

    private int baseForBalls;

    private int hitByPitches;

    private int atBat;

    private int runs;

    private int hits;

    private int doubleHit;

    private int tripleHit;

    private int homeRun;

    private int runBattedIn;

    private int strikeOut;

    private int stolenBase;

    private int caughtStealing;

    private int sacrificeFlies;

    private int totalBases;

    private Double battingAverage;

    private Double onBasePercantage;

    private Double sluggingPercentage;

    private Double onBaseSlugging;

    private Player player;

    private Game game;

    public GameHittingDetails(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlateAppearences() {
        return plateAppearences;
    }

    public void setPlateAppearences(int plateAppearences) {
        this.plateAppearences = plateAppearences;
    }

    public int getSacrificeHits() {
        return sacrificeHits;
    }

    public void setSacrificeHits(int sacrificeHits) {
        this.sacrificeHits = sacrificeHits;
    }

    public int getBaseForBalls() {
        return baseForBalls;
    }

    public void setBaseForBalls(int baseForBalls) {
        this.baseForBalls = baseForBalls;
    }

    public int getHitByPitches() {
        return hitByPitches;
    }

    public void setHitByPitches(int hitByPitches) {
        this.hitByPitches = hitByPitches;
    }

    public int getAtBat() {
        return atBat;
    }

    public void setAtBat(int atBat) {
        this.atBat = atBat;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getDoubleHit() {
        return doubleHit;
    }

    public void setDoubleHit(int doubleHit) {
        this.doubleHit = doubleHit;
    }

    public int getTripleHit() {
        return tripleHit;
    }

    public void setTripleHit(int tripleHit) {
        this.tripleHit = tripleHit;
    }

    public int getHomeRun() {
        return homeRun;
    }

    public void setHomeRun(int homeRun) {
        this.homeRun = homeRun;
    }

    public int getRunBattedIn() {
        return runBattedIn;
    }

    public void setRunBattedIn(int runBattedIn) {
        this.runBattedIn = runBattedIn;
    }

    public int getStrikeOut() {
        return strikeOut;
    }

    public void setStrikeOut(int strikeOut) {
        this.strikeOut = strikeOut;
    }

    public int getStolenBase() {
        return stolenBase;
    }

    public void setStolenBase(int stolenBase) {
        this.stolenBase = stolenBase;
    }

    public int getCaughtStealing() {
        return caughtStealing;
    }

    public void setCaughtStealing(int caughtStealing) {
        this.caughtStealing = caughtStealing;
    }

    public int getSacrificeFlies() {
        return sacrificeFlies;
    }

    public void setSacrificeFlies(int sacrificeFlies) {
        this.sacrificeFlies = sacrificeFlies;
    }

    public int getTotalBases() {
        return totalBases;
    }

    public void setTotalBases(int totalBases) {
        this.totalBases = totalBases;
    }

    public Double getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(Double battingAverage) {
        this.battingAverage = battingAverage;
    }

    public Double getOnBasePercantage() {
        return onBasePercantage;
    }

    public void setOnBasePercantage(Double onBasePercantage) {
        this.onBasePercantage = onBasePercantage;
    }

    public Double getSluggingPercentage() {
        return sluggingPercentage;
    }

    public void setSluggingPercentage(Double sluggingPercentage) {
        this.sluggingPercentage = sluggingPercentage;
    }

    public Double getOnBaseSlugging() {
        return onBaseSlugging;
    }

    public void setOnBaseSlugging(Double onBaseSlugging) {
        this.onBaseSlugging = onBaseSlugging;
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
        return "GameHittingDetails{" +
                "id=" + id +
                ", plateAppearences=" + plateAppearences +
                ", sacrificeHits=" + sacrificeHits +
                ", baseForBalls=" + baseForBalls +
                ", hitByPitches=" + hitByPitches +
                ", atBat=" + atBat +
                ", runs=" + runs +
                ", hits=" + hits +
                ", doubleHit=" + doubleHit +
                ", tripleHit=" + tripleHit +
                ", homeRun=" + homeRun +
                ", runBattedIn=" + runBattedIn +
                ", strikeOut=" + strikeOut +
                ", stolenBase=" + stolenBase +
                ", caughtStealing=" + caughtStealing +
                ", sacrificeFlies=" + sacrificeFlies +
                ", totalBases=" + totalBases +
                ", battingAverage=" + battingAverage +
                ", onBasePercantage=" + onBasePercantage +
                ", sluggingPercentage=" + sluggingPercentage +
                ", onBaseSlugging=" + onBaseSlugging +
                ", player=" + player +
                ", game=" + game +
                '}';
    }
}
