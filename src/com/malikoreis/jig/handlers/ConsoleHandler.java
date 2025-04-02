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

        System.out.println("$ 1 : View currency");
        System.out.println("$ 2 : Upgrades");
        System.out.println("$ 3 : Settings");
        System.out.println("$ 4 : Exit the game");
        System.out.print("$ ");
        int input = sc.nextInt();

        switch (input) {
            case 1:
                System.out.printf("$ Current Currency : %.2f%n", Money.getMoney()); // Formatlı
                ConsoleHandler.scheduleClearConsole();
                break;
            case 2:
                System.out.println("$ 1 : Decrease delay");
                System.out.printf("$ 2 : %.2fx Money%n", GlobalVariables.multiplyAmount); // Formatlı
                System.out.println("$ 3 : Increase amount");
                System.out.println("$ 4 : Exit");
                System.out.print("$ ");
                int input_u = sc.nextInt();
                switch (input_u) {
                    case 1:
                        if (Money.getMoney() >= GlobalVariables.delayCost) {
                            System.out.println("$ Old delay : " + Delay.getDelay());
                            DelayHandler.increaseTimer(Amount.getTimerAmount());
                            System.out.println("$ New delay : " + Delay.getDelay());
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.delayCost);
                            System.out.printf("$ Remaining Money : %.2f%n", Money.getMoney()); // Formatlı
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.delayCost = GlobalVariables.delayCost * 2;
                        } else {
                            System.out.printf("$ You need at least %.2f money!%n", GlobalVariables.delayCost); // Formatlı
                            ConsoleHandler.scheduleClearConsole();
                        }
                        break;
                    case 2:
                        if (Money.getMoney() >= GlobalVariables.moneyCost) {
                            System.out.printf("$ Old Money : %.2f%n", Money.getMoney()); // Formatlı
                            MoneyHandler.multiplyMoney(GlobalVariables.multiplyAmount);
                            System.out.printf("$ New Money : %.2f%n", Money.getMoney()); // Formatlı
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.moneyCost);
                            System.out.printf("$ Remaining Money : %.2f%n", Money.getMoney()); // Formatlı
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.moneyCost = GlobalVariables.moneyCost * 2;
                        } else {
                            System.out.printf("$ You need at least %.2f money!%n", GlobalVariables.moneyCost); // Formatlı
                            ConsoleHandler.scheduleClearConsole();
                        }
                        break;
                    case 3:
                        if (Money.getMoney() >= GlobalVariables.amountCost) {
                            System.out.printf("$ Old Amount : %.2f%n", Amount.getAmount());
                            Amount.increaseAmount();
                            System.out.printf("$ New Amount : %.2f%n", Amount.getAmount());
                            MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.amountCost);
                            System.out.printf("$ Remaining Money : %.2f%n", Money.getMoney()); // Formatlı
                            ConsoleHandler.scheduleClearConsole();
                            GlobalVariables.amountCost = GlobalVariables.amountCost * 2;
                        } else {
                            System.out.printf("$ You need at least %.2f money!%n", GlobalVariables.moneyCost); // Formatlı
                            ConsoleHandler.scheduleClearConsole(); 
                        }
                        break;
                    case 4:
                        scheduleClearConsole();
                        break;
                    default:
                        System.out.println("$ Invalid selection. Going to the menu...");
                        scheduleClearConsole();
                        break;
                }
                break;
            case 3:
                System.out.println("$ 1 : Change Menu wait delay");
                System.out.println("$ 2 : Exit");
                System.out.print("$ ");
                int input_s = sc.nextInt();

                switch (input_s) {
                    case 1:
                        System.out.printf("$ Old menu wait delay : %s", GlobalVariables.getClearDelay());
                        System.out.print("\n$ Enter new menu wait delay (ms) : ");
                        long input_mwd = sc.nextInt();
                        GlobalVariables.setClearDelay(input_mwd);
                        System.out.printf("$ New menu wait delay : %s", GlobalVariables.getClearDelay());
                        ConsoleHandler.scheduleClearConsole();
                        break;
                    case 2:
                        scheduleClearConsole();
                        break;
                    default:
                        System.out.println("$ Invalid selection. Going to the menu...");
                        scheduleClearConsole();
                        break;
                }

                break;
            case 4:
                System.exit(0);
                break;
            default:
            System.out.println("$ Invalid selection. Clearing the menu...");
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
            System.out.println("$ Error while cleaning the console : " + e.getMessage());
        }
    }
}