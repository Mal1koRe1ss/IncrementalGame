// LanguageHandler.java
package com.malikoreis.jig.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

public class LanguageHandler {

    private static final String LANGUAGE_DIR = "./lang/";
    private static final String DEFAULT_LANGUAGE = "en_US";
    private static String currentLanguage = DEFAULT_LANGUAGE;
    private static Map<String, String> translations = new HashMap<>();
    private static final String LANGUAGE_PREFERENCE_KEY = "currentLanguage";

    // Tercihleri (Preferences API) kullanarak dil ayarını kaydetme ve yükleme
    private static Preferences prefs = Preferences.userRoot().node(LanguageHandler.class.getName());

    public static void initialize() {
        loadLanguagePreference();
        loadLanguage(currentLanguage);
    }

    public static void setLanguage(String language) {
        if (language == null || language.isEmpty()) {
            System.err.println("$ Invalid language code. Using default: " + DEFAULT_LANGUAGE);
            language = DEFAULT_LANGUAGE;
        }

        if (!language.equals(currentLanguage)) {
            loadLanguage(language);
            currentLanguage = language;
            saveLanguagePreference(language); // Tercihi kaydet
        }
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }

    public static String translate(String key) {
        return translations.getOrDefault(key, key);
    }

    private static void loadLanguage(String language) {
        translations.clear();
        String filePath = LANGUAGE_DIR + language + ".properties";
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("$ Language file not found: " + filePath + ". Using default language.");
            language = DEFAULT_LANGUAGE;
            filePath = LANGUAGE_DIR + language + ".properties";
            file = new File(filePath);
            if (!file.exists()) {
                System.err.println("$ Default language file not found: " + filePath + ". No translations will be loaded.");
                return;
            }
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)) {

            Properties props = new Properties();
            props.load(inputStreamReader);

            props.forEach((k, v) -> translations.put((String) k, (String) v));
            System.out.println("$ Loaded language: " + language + " from " + filePath);

        } catch (IOException e) {
            System.err.println("$ Error loading language file: " + filePath + " - " + e.getMessage());
        }
    }

    // Kaydedilmiş dil tercihini yükle
    private static void loadLanguagePreference() {
        currentLanguage = prefs.get(LANGUAGE_PREFERENCE_KEY, DEFAULT_LANGUAGE);
        System.out.println("$ Loaded language preference: " + currentLanguage);
    }

    // Dil tercihini kaydet
    private static void saveLanguagePreference(String language) {
        prefs.put(LANGUAGE_PREFERENCE_KEY, language);
        try {
            prefs.flush(); // Verileri diske yaz
            System.out.println("$ Saved language preference: " + language);
        } catch (Exception e) {
            System.err.println("$ Error saving language preference: " + e.getMessage());
        }
    }
}
