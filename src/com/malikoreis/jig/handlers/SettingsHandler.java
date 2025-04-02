package com.malikoreis.jig.handlers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

import com.malikoreis.jig.GlobalVariables;

public class SettingsHandler {

    private static final String DATA_DIR = "./data/";
    private static final String DATA_FILE = "settings.txt";
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
                    + "\"ClearDelay\":" + GlobalVariables.getClearDelay() + "}";

            Files.write(Paths.get(DATA_DIR + DATA_FILE), json.getBytes());

        } catch (IOException e) {
            System.err.println(LanguageHandler.translate("settings.save.error") + e.getMessage());
        }
    }

    // Veri yükleme
    public static synchronized void loadSettings() {
        try {
            setupDataDirectory();
            File file = new File(DATA_DIR + DATA_FILE);

            if (file.exists()) {
                String json = new String(Files.readAllBytes(file.toPath()));

                // JSON'u manuel parse et
                double clearDelay = getDoubleValue(json, "ClearDelay");

                // Değerleri uygula (Setter kullanarak)
                GlobalVariables.setClearDelay(clearDelay); // <-- DÜZELTİLDİ

                // Yüklenen verileri göster
                System.out.printf(LanguageHandler.translate("settings.loaded.waitdelay"), clearDelay);
                System.out.println("╚══════════════════════════════╝");

                Thread.sleep(GlobalVariables.getClearDelay());

            } else {
                System.out.println(LanguageHandler.translate("settings.default"));
                Thread.sleep(1000);
            }

            ConsoleHandler.clearConsole();

        } catch (Exception e) {
            System.err.println(LanguageHandler.translate("settings.load.error") + e.getMessage());
        }
    }

    // JSON'dan double değer çekme
    private static double getDoubleValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1)
            endIndex = json.indexOf("}", startIndex);
        return Double.parseDouble(json.substring(startIndex, endIndex));
    }

    // Kapanışta kayıt
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(LanguageHandler.translate("settings.save.exit"));
            saveData();
            autoSaveTimer.cancel();
        }));
    }
}
