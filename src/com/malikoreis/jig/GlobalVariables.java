package com.malikoreis.jig;

public class GlobalVariables {

    private static long ClearDelay = 1000;
    public static double increaseAmount = 1.15;
    public static double multiplyAmount = 1.75;
    public static double timerAmount = 0.75;
    public static double moneyAmount = 1;
    public static double delayCost = 9;
    public static double moneyCost = 7;
    public static double amountCost = 5;
    public static String selectedLanguage = "en_US";
    
    public static long getClearDelay() {
        return ClearDelay;
    }

    public static void setClearDelay(double delay) {
        ClearDelay = (long) delay; // clearDelay long olmalı
    } 

}
