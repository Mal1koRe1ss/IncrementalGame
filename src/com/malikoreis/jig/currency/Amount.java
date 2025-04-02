package com.malikoreis.jig.currency;

import com.malikoreis.jig.GlobalVariables;

public class Amount {

    public static double getAmount() {
        return GlobalVariables.moneyAmount;
    }

    public static double getTimerAmount() {
        return GlobalVariables.timerAmount;
    }

    public static double increaseAmount() {
        return GlobalVariables.moneyAmount *= GlobalVariables.increaseAmount;
    }

    public static double setAmount(double amount) {
        return GlobalVariables.moneyAmount = amount;
    }
}
