package com.malikoreis.jig.handlers;

import java.util.Timer;
import java.util.TimerTask;

import com.malikoreis.jig.currency.Amount;
import com.malikoreis.jig.currency.Delay;

public class MoneyHandler {

    public static double money = 1.0;
    private static Timer timer;

    public static void increaseMoney(double amount) {
        money += amount;
    }

    public static void multiplyMoney(double amount) {
        money *= amount;
    }

    public static void resetMoney() {
        money = 1.0;
    }

    public static void autoMoney() {
        double time = Delay.getDelay(); // DelayHandler'dan süreyi al
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                money += Amount.amount; // Her saniye Amount'daki miktar kadar para ekle
            }
        }, 0, (long) time); // Her DelayHandler.time ms tekrarla
    }

    public static void stopAutoMoney() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
