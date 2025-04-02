import com.malikoreis.jig.handlers.*;

public class Game {
    public static void main(String[] args) {
        LanguageHandler.initialize();
        DataHandler.registerShutdownHook();
        DataHandler.loadData(); // Verileri yükle ve göster
        DataHandler.startAutoSave();
        SettingsHandler.registerShutdownHook();
        SettingsHandler.loadSettings(); // Verileri yükle ve göster
        SettingsHandler.startAutoSave();
        ConsoleHandler.ShowMenu();
        MoneyHandler.autoMoney();
    }
}
