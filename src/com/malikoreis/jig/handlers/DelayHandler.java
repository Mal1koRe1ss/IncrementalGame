package com.malikoreis.jig.handlers;

public class DelayHandler {

    public static double time = 1000; // 1000ms = 1s

    public static void increaseTimer(double amount) {
        time *= amount;
    }

    public static void resetTimer() {
        time = 1000;
    }

}
