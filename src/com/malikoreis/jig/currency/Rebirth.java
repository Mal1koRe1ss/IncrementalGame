package com.malikoreis.jig.currency;

import com.malikoreis.jig.GlobalVariables;
import com.malikoreis.jig.handlers.MoneyHandler;
import com.malikoreis.jig.currency.Rebirth;
import com.malikoreis.jig.handlers.DataHandler;

public class Rebirth {

    public static void ResetAll() {
        ResetCurrency();
        ResetVariables();
        changeRebirthCurrency();
        DataHandler.saveData();
    }

    private static void ResetCurrency() {
        MoneyHandler.setMoney(GlobalVariables.DEFAULT_MONEY);
        Delay.setDelay(GlobalVariables.DEFAULT_DELAY);
        Amount.setAmount(GlobalVariables.DEFAULT_AMOUNT);
    }

    private static void ResetVariables() {
        GlobalVariables.increaseAmount = 1.75;
        GlobalVariables.multiplyAmount = 2;
        GlobalVariables.timerAmount = 0.75;
        GlobalVariables.moneyAmount = 2;
        GlobalVariables.delayCost = 9;
        GlobalVariables.moneyCost = 7;
        GlobalVariables.amountCost = 5;
    }

    private static void changeRebirthCurrency() {
        GlobalVariables.rebirthCost = GlobalVariables.rebirthCost*2;
    } 

    public static double getCost() {
        return GlobalVariables.rebirthCost;
    }
    
}
