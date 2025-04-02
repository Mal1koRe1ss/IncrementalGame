package com.malikoreis.jig.currency;

import com.malikoreis.jig.handlers.DelayHandler;

public class Delay {

    public static double getDelay() {
        return DelayHandler.time;
    }

    public static double setDelay(double delay) {
        return DelayHandler.time = delay;
    }

}
