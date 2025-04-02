import java.util.Scanner;
import com.malikoreis.jig.currency.*;
import com.malikoreis.jig.handlers.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game {


    public static void main(String[] args) {

        int choice = ConsoleHandler.ShowMenu();

        switch (choice) {
            case 1:
                System.out.print("$ Current Currency : " + Money.getMoney());
                ConsoleHandler.scheduleClearConsole();
                break;
            case 2:
                System.out.print("$ Old delay : " + Delay.getDelay());
                DelayHandler.increaseTimer(Amount.getAmount());
                System.out.print("\n$ New delay : " + Delay.getDelay());
                ConsoleHandler.scheduleClearConsole();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                break;
        }

        MoneyHandler.autoMoney();
    }

    

}
