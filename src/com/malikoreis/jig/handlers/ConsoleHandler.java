// ConsoleHandler.java
package com.malikoreis.jig.handlers;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.spi.ResourceBundleProvider;
import java.util.InputMismatchException;
import com.malikoreis.jig.GlobalVariables;
import com.malikoreis.jig.currency.Amount;
import com.malikoreis.jig.currency.Delay;
import com.malikoreis.jig.currency.Money;
import com.malikoreis.jig.currency.Rebirth;

public class ConsoleHandler {
    private static Timer consoleTimer = new Timer();
    private static volatile boolean isAutoUpdateActive = false;
    private static Timer updateTimer;

    public static void ShowMenu() {
        Scanner sc = new Scanner(System.in);
        
        try {
            printMainMenu();
            int input = sc.nextInt();
            handleMainMenu(input, sc);
        } catch (InputMismatchException e) {
            handleInvalidInput(sc);
        }
    }

    private static void printMainMenu() {
        System.out.println(LanguageHandler.translate("menu.main.view_currency"));
        System.out.println(LanguageHandler.translate("menu.main.upgrades"));
        System.out.println(LanguageHandler.translate("menu.main.settings"));
        System.out.println(LanguageHandler.translate("menu.main.rebirth"));
        System.out.println(LanguageHandler.translate("menu.main.exit"));
        System.out.print("$ ");
    }

    private static void handleMainMenu(int input, Scanner sc) {
        switch (input) {
            case 1:
                handleViewCurrency();
                break;
            case 2:
                handleUpgradesMenu(sc);
                return;
            case 3:
                handleSettingsMenu(sc);
                return;
            case 4:
                handleRebirth(sc);
                break;
            case 5:
                System.exit(0);
                break;
            default:
                handleInvalidSelection();
        }
        scheduleClearConsole();
    }

    private static void handleViewCurrency() {
        System.out.printf(LanguageHandler.translate("currency.current"), Money.getMoney());
    }

    private static void handleUpgradesMenu(Scanner sc) {
        try {
            printUpgradesMenu();
            int input = sc.nextInt();
            processUpgradeChoice(input, sc);
        } catch (InputMismatchException e) {
            handleInvalidInput(sc);
        }
        scheduleClearConsole();
    }

    private static void printUpgradesMenu() {
        System.out.printf(LanguageHandler.translate("menu.upgrades.decrease.delay"), GlobalVariables.delayCost);
        System.out.printf(LanguageHandler.translate("menu.upgrades.multiply.money"), GlobalVariables.multiplyAmount, GlobalVariables.moneyCost);
        System.out.printf(LanguageHandler.translate("menu.upgrades.increase.amount"), GlobalVariables.amountCost);
        System.out.println(LanguageHandler.translate("menu.upgrades.exit"));
        System.out.print("$ ");
    }

    private static void processUpgradeChoice(int input, Scanner sc) {
        switch (input) {
            case 1:
                if (Money.getMoney() >= GlobalVariables.delayCost) {
                    System.out.println(LanguageHandler.translate("upgrades.old.delay") + Delay.getDelay());
                    DelayHandler.increaseTimer(Amount.getTimerAmount());
                    System.out.println(LanguageHandler.translate("upgrades.new.delay") + Delay.getDelay());
                    MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.delayCost);
                    System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                    GlobalVariables.delayCost *= 2;
                } else {
                    System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.delayCost);
                }
                break;
            case 2:
                if (Money.getMoney() >= GlobalVariables.moneyCost) {
                    System.out.printf(LanguageHandler.translate("upgrades.old.money"), Money.getMoney());
                    MoneyHandler.multiplyMoney(GlobalVariables.multiplyAmount);
                    System.out.printf(LanguageHandler.translate("upgrades.new.money"), Money.getMoney());
                    MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.moneyCost);
                    System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                    GlobalVariables.moneyCost *= 2;
                } else {
                    System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.moneyCost);
                }
                break;
            case 3:
                if (Money.getMoney() >= GlobalVariables.amountCost) {
                    System.out.printf(LanguageHandler.translate("upgrades.old.amount"), Amount.getAmount());
                    Amount.increaseAmount();
                    System.out.printf(LanguageHandler.translate("upgrades.new.amount"), Amount.getAmount());
                    MoneyHandler.setMoney(Money.getMoney() - GlobalVariables.amountCost);
                    System.out.printf(LanguageHandler.translate("upgrades.remaining.money"), Money.getMoney());
                    GlobalVariables.amountCost *= 2;
                } else {
                    System.out.printf(LanguageHandler.translate("upgrades.notenough.money"), GlobalVariables.amountCost);
                }
                break;
            case 4:
                break;
            default:
                handleInvalidSelection();
        }
    }

    private static void handleSettingsMenu(Scanner sc) {
        try {
            printSettingsMenu();
            int input = sc.nextInt();
            processSettingsChoice(input, sc);
        } catch (InputMismatchException e) {
            handleInvalidInput(sc);
        }
        scheduleClearConsole();
    }

    private static void handleRebirth(Scanner sc) {
        clearConsole();
        try {
            if(Money.getMoney() < GlobalVariables.rebirthCost) {
                System.out.printf(LanguageHandler.translate("rebirth.notenough"), GlobalVariables.rebirthCost - Money.getMoney());
                waitForConfirmation();
                scheduleClearConsole(); // Eklendi
                return;
            }
    
            System.out.printf(LanguageHandler.translate("menu.rebirth.check"), GlobalVariables.rebirthCost);
            System.out.print(LanguageHandler.translate("menu.rebirth.confirm"));
            System.out.print("$ ");
            
            sc.nextLine();
            String input = sc.nextLine().trim().toUpperCase();
    
            processRebirthChoice(input, sc);
        } catch (Exception e) {
            handleInvalidInput(sc);
        }
    }
    
    // processRebirthChoice() metodunu düzelt
    private static void processRebirthChoice(String input, Scanner sc) {
        switch(input) {
            case "Y":
                Rebirth.ResetAll();
                System.out.println(LanguageHandler.translate("rebirth.success"));
                break;
            case "N":
                System.out.println(LanguageHandler.translate("rebirth.cancelled"));
                break;
            default:
                System.out.println(LanguageHandler.translate("invalid.selection"));
        }
        waitForConfirmation();
        scheduleClearConsole(); // Eklendi
    }
    
    private static void waitForConfirmation() {
        try {
            Thread.sleep(GlobalVariables.getClearDelay()); // 2 saniye bekle
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void printSettingsMenu() {
        System.out.println(LanguageHandler.translate("menu.settings.change.waitdelay"));
        System.out.println(LanguageHandler.translate("menu.settings.lang"));
        System.out.println(LanguageHandler.translate("menu.settings.exit"));
        System.out.print("$ ");
    }

    private static void processSettingsChoice(int input, Scanner sc) {
        switch (input) {
            case 1:
                System.out.printf(LanguageHandler.translate("settings.old.waitdelay"), GlobalVariables.getClearDelay());
                System.out.print(LanguageHandler.translate("settings.enter.waitdelay"));
                try {
                    long newDelay = sc.nextLong();
                    GlobalVariables.setClearDelay(newDelay);
                    System.out.printf(LanguageHandler.translate("settings.new.waitdelay"), GlobalVariables.getClearDelay());
                } catch (InputMismatchException e) {
                    handleInvalidInput(sc);
                }
                break;
            case 2:
                handleLanguageChange(sc);
                break;
            case 3:
                break;
            default:
                handleInvalidSelection();
        }
    }

    private static void handleLanguageChange(Scanner sc) {
        try {
            System.out.println(LanguageHandler.translate("menu.settings.lang.select"));
            System.out.println(LanguageHandler.translate("menu.settings.lang.en_us"));
            System.out.println(LanguageHandler.translate("menu.settings.lang.tr_tr"));
            System.out.print("$ ");
            int choice = sc.nextInt();
            String lang = choice == 2 ? "tr_TR" : "en_US";
            LanguageHandler.setLanguage(lang);
            System.out.println(LanguageHandler.translate("menu.settings.lang.change") + lang);
        } catch (InputMismatchException e) {
            handleInvalidInput(sc);
        }
    }

    private static void handleInvalidInput(Scanner sc) {
        sc.nextLine();
        System.out.println(LanguageHandler.translate("invalid.selection"));
        scheduleClearConsole();
    }

    private static void handleInvalidSelection() {
        System.out.println(LanguageHandler.translate("invalid.selection"));
        scheduleClearConsole();
    }

    public static void scheduleClearConsole() {
        consoleTimer.cancel();
        consoleTimer = new Timer();
        consoleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                clearConsole();
                ShowMenu();
            }
        }, GlobalVariables.getClearDelay());
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
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