package com.junak.scorekeeper.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final List<String> defendingPositions = new ArrayList<>(
            Arrays.asList(
                    "OUT",
                    "P",
                    "C",
                    "1B",
                    "2B",
                    "3B",
                    "SS",
                    "LF",
                    "CF",
                    "RF",
                    "OF",
                    "DH"
            ));
    public static final String pitcher = "p";
    public static final String catcher = "c";
    public static final String defenderFirstBase = "1b";
    public static final String defenderSecondBase = "2b";
    public static final String shortStop = "ss";
    public static final String defenderThirdBase = "3b";
    public static final String rightField = "rf";
    public static final String centerField = "cf";
    public static final String leftField = "lf";

    public static final String runnerFirstBase = "1b";
    public static final String runnerSecondBase = "2b";
    public static final String runnerThirdBase = "3b";
    public static final String runnerHomeBase = "hb";
    public static final String batter = "batter";
}
