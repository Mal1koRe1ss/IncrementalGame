package com.malikoreis.jig.handlers;

import java.util.Timer;
import java.util.TimerTask;

import com.malikoreis.jig.GlobalVariables;
import com.malikoreis.jig.currency.Delay;

public class MoneyHandler {

    public static double money = 1.0;
    private static Timer timer;

    public static void increaseMoney() {
        money += GlobalVariables.moneyAmount;
    }

    public static void multiplyMoney(double amount) {
        money *= amount;
    }

    public static void resetMoney() {
        money = 1.0;
    }

    public static void autoMoney() {
        double time = Delay.getDelay();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                money += GlobalVariables.moneyAmount;
            }
        }, 0, (long) time);
    }

    public static void stopAutoMoney() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public static void setMoney(double newMoney) {
        money = newMoney; // MoneyHandler'da money değişkeni olmalı
    }
}
