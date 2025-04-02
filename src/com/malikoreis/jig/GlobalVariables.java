package com.malikoreis.jig;

public class GlobalVariables {

    private static long ClearDelay = 1000;
    public static double increaseAmount = 1.5;
    public static double multiplyAmount = 1.75;
    public static double timerAmount = 0.75;
    public static double moneyAmount = 1;
    public static double delayCost = 9;
    public static double moneyCost = 7;
    public static double amountCost = 5;
    public static String selectedLanguage = "en_US";
    public static double DEFAULT_MONEY = 1.0;
    public static double DEFAULT_DELAY = 1000;
    public static double DEFAULT_AMOUNT = 1.0;
    public static double rebirthCost = 10000;

    public static long getClearDelay() {
        return ClearDelay;
    }

    public static void setClearDelay(double delay) {
        ClearDelay = (long) delay; // clearDelay long olmalı
    } 

}
