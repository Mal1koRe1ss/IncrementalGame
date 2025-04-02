package com.malikoreis.jig.handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import com.malikoreis.jig.GlobalVariables;
import com.malikoreis.jig.currency.Amount;
import com.malikoreis.jig.currency.Delay;
import com.malikoreis.jig.currency.Money;

public class DataHandler {

    private static final String DATA_DIR = "./data/";
    private static final String DATA_FILE = "save.txt";
    private static Timer autoSaveTimer;

    // Dizin kontrolü
    private static void setupDataDirectory() throws IOException {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Otomatik kayıt başlatma
    public static void startAutoSave() {
        autoSaveTimer = new Timer();
        autoSaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                saveData();
            }
        }, 10000, 10000); // 10 saniyede bir
    }

    // Veri kaydetme (JSON formatında)
    public static synchronized void saveData() {
        try {
            setupDataDirectory();

            // JSON formatını manuel oluştur
            String json = "{"
                    + "\"money\":" + Money.getMoney() + ","
                    + "\"delayCost\":" + GlobalVariables.delayCost + ","
                    + "\"moneyCost\":" + GlobalVariables.moneyCost + ","
                    + "\"amountCost\":" + GlobalVariables.amountCost + ","
                    + "\"delay\":" + Delay.getDelay() + ","
                    + "\"amount\":" + Amount.getAmount() + ","
                    + "\"multiplyAmount\":" + GlobalVariables.multiplyAmount
                    + "}";

            Files.write(Paths.get(DATA_DIR + DATA_FILE), json.getBytes());
        } catch (IOException e) {
            System.err.println(LanguageHandler.translate("data.save.error") + e.getMessage());
        }
    }

    // Veri yükleme
    public static synchronized void loadData() {
        try {
            setupDataDirectory();
            File file = new File(DATA_DIR + DATA_FILE);

            if (file.exists()) {
                String json = new String(Files.readAllBytes(file.toPath()));

                // JSON'u manuel parse et
                double money = getDoubleValue(json, "money");
                double delayCost = getDoubleValue(json, "delayCost");
                double moneyCost = getDoubleValue(json, "moneyCost");
                double amountCost = getDoubleValue(json, "amountCost");
                int delay = getIntValue(json, "delay");
                int amount = getIntValue(json, "amount");
                double multiplyAmount = getDoubleValue(json, "multiplyAmount");

                // Değerleri uygula
                MoneyHandler.setMoney(money);
                GlobalVariables.delayCost = delayCost;
                GlobalVariables.moneyCost = moneyCost;
                GlobalVariables.amountCost = amountCost;
                Delay.setDelay(delay);
                Amount.setAmount(amount);
                GlobalVariables.multiplyAmount = multiplyAmount;

                // Yüklenen verileri göster
                System.out.println("╔══════════════════════════════╗");
                System.out.printf(LanguageHandler.translate("data.loaded.money"), money);
                System.out.printf(LanguageHandler.translate("data.loaded.delay"), delayCost);
                System.out.printf(LanguageHandler.translate("data.loaded.multiplyamount"), multiplyAmount);

                Thread.sleep(GlobalVariables.getClearDelay()); // 2 saniye gösterim
            } else {
                System.out.println(LanguageHandler.translate("data.none"));
                Thread.sleep(1000);

                ConsoleHandler.clearConsole();
            }
        } catch (Exception e) {
            System.err.println(LanguageHandler.translate("data.load.error") + e.getMessage());
        }
    }

    // JSON'dan double değer çekme
    private static double getDoubleValue(String json, String key) {
        int keyStart = json.indexOf("\"" + key + "\":");
        if (keyStart == -1) throw new RuntimeException("Key not found: " + key);

        int valueStart = keyStart + key.length() + 3;
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) valueEnd = json.indexOf("}", valueStart);

        String valueStr = json.substring(valueStart, valueEnd);
        return Double.parseDouble(valueStr);
    }

    // JSON'dan int değer çekme
    private static int getIntValue(String json, String key) {
        return (int) getDoubleValue(json, key);
    }

    // Kapanışta kayıt
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(LanguageHandler.translate("data.save.exit"));
            saveData();
            autoSaveTimer.cancel();
        }));
    }
}
