// ConsoleHandler.java
package com.malikoreis.jig.handlers;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import com.malikoreis.jig.GlobalVariables;
import com.malikoreis.jig.currency.Amount;
import com.malikoreis.jig.currency.Delay;
import com.malikoreis.jig.currency.Money;

public class ConsoleHandler {

    public static void ShowMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println(LanguageHandler.translate("menu.main.view_currency"));
        System.out.println(LanguageHandler.translate("menu.main.upgrades"));
        System.out.println(LanguageHandler.translate("menu.main.settings"));
        System.out.println(LanguageHandler.translate("menu.main.exit"));
        System.out.print(LanguageHandler.translate(""));

        int input = sc.nextInt();

        switch (input) {
            case 1:
                System.out.printf(LanguageHandler.translate("currency.current"), Money.getMoney());
                ConsoleHandler.scheduleClearConsole();
                break;
            case 2:
                System.out.println(LanguageHandler.translate("menu.upgrades.decrease.delay"));
                System.out.printf(LanguageHandler.translate("menu.upgrades.multiply.money"), GlobalVariables.multiplyAmount);
                System.out.println(LanguageHandler.translate("menu.upgrades.increase.amount"));
                System.out.println(LanguageHandler.translate("menu.upgrades.exit"));
                System.out.print(LanguageHandler.translate(""));

                int input_u = sc.nextInt();

                switch (input_u) {
                    case 1:
                        if (Money.getMoney() >= GlobalVariables.delayCost) {
                            System.out.println(LanguageHandler.translate("upgrades.old.delay") + Delay.getDelay());
                            DelayHandler.increaseTimer(Amount.getTimerAmount());
                            System.out.println(LanguageHandler.translate("upgrades.new.delay") + Delay.getDelay());
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.delayCost);
                            System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.delayCost = GlobalVariables.delayCost * 2;
                        } else {
                            System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.delayCost);
                            ConsoleHandler.scheduleClearConsole();
                        }
                        break;
                    case 2:
                        if (Money.getMoney() >= GlobalVariables.moneyCost) {
                            System.out.printf(LanguageHandler.translate("upgrades.old.money"), Money.getMoney());
                            MoneyHandler.multiplyMoney(GlobalVariables.multiplyAmount);
                            System.out.printf(LanguageHandler.translate("upgrades.new.money"), Money.getMoney());
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.moneyCost);
                            System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.moneyCost = GlobalVariables.moneyCost * 2;
                        } else {
                            System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.moneyCost);
                            ConsoleHandler.scheduleClearConsole();
                        }
                        break;
                    case 3:
                        if (Money.getMoney() >= GlobalVariables.amountCost) {
                            System.out.printf(LanguageHandler.translate("upgrades.old.amount"), Amount.getAmount());
                            Amount.increaseAmount();
                            System.out.printf(LanguageHandler.translate("upgrades.new.amount"), Amount.getAmount());
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.amountCost);
                            System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.amountCost = GlobalVariables.amountCost * 2;
                        } else {
                            System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.moneyCost);
                            ConsoleHandler.scheduleClearConsole();
                        }
                        break;
                    case 4:
                        scheduleClearConsole();
                        break;
                    default:
                        System.out.println(LanguageHandler.translate("invalid.selection"));
                        scheduleClearConsole();
                        break;
                }
                break;
            case 3:
                System.out.println(LanguageHandler.translate("menu.settings.change.waitdelay"));
                System.out.println(LanguageHandler.translate("menu.settings.lang"));
                System.out.println(LanguageHandler.translate("menu.settings.exit"));
                System.out.print(LanguageHandler.translate(""));

                int input_s = sc.nextInt();

                switch (input_s) {
                    case 1:
                        System.out.printf(LanguageHandler.translate("settings.old.waitdelay"), GlobalVariables.getClearDelay());
                        System.out.print(LanguageHandler.translate("settings.enter.waitdelay"));
                        long input_mwd = sc.nextInt();
                        GlobalVariables.setClearDelay(input_mwd);
                        System.out.printf(LanguageHandler.translate("settings.new.waitdelay"), GlobalVariables.getClearDelay());
                        ConsoleHandler.scheduleClearConsole();
                        break;
                    case 2:
                        System.out.println(LanguageHandler.translate("menu.settings.lang.select"));
                        System.out.println(LanguageHandler.translate("menu.settings.lang.en_us"));
                        System.out.println(LanguageHandler.translate("menu.settings.lang.tr_tr"));
                        System.out.print("$ ");
                        int langChoice = sc.nextInt();
                        String newLanguage = "en_US";
                        switch (langChoice) {
                            case 1:
                                newLanguage = "en_US";
                                break;
                            case 2:
                                newLanguage = "tr_TR";
                                break;
                            default:
                                System.out.println(LanguageHandler.translate("invalid.selection"));
                                scheduleClearConsole();
                                return;
                        }
                        LanguageHandler.setLanguage(newLanguage);
                        System.out.println(LanguageHandler.translate("menu.settings.lang.change") + LanguageHandler.getCurrentLanguage());
                        scheduleClearConsole();
                        break;
                    case 3:
                        scheduleClearConsole();
                        break;
                    default:
                        System.out.println(LanguageHandler.translate("invalid.selection"));
                        scheduleClearConsole();
                        break;
                }
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println(LanguageHandler.translate("invalid.selection"));
                scheduleClearConsole();
                break;
        }
    }

    public static void scheduleClearConsole() {
        long clearDelay = GlobalVariables.getClearDelay();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clearConsole();
                ShowMenu();
            }
        }, clearDelay);
    }

    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println(LanguageHandler.translate("console.clear.error") + e.getMessage());
        }
    }
}
