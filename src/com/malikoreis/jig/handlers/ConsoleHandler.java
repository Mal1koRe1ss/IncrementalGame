package com.malikoreis.jig.handlers;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.malikoreis.jig.GlobalVariables;

public class ConsoleHandler {

    private static int input; // input değişkenini sınıf seviyesinde tanımla

    public static int ShowMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("$ 1 : View currency");
        System.out.println("$ 2 : Upgrade timer");
        System.out.println("$ 3 : Exit the game");
        System.out.print("$ ");
        int input = sc.nextInt();

        return input;
    }

    public static int GetInput() {
        return input;
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
