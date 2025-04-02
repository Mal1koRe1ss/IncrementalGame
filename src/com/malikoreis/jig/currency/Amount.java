package com.malikoreis.jig.currency;


public class Amount {

    public static double amount = 1.25; // 1000ms = 1s

    public static double getAmount() {
        return amount;
    }

    public static double increaseAmount() {
        return amount *= 1.15;
    }

}
