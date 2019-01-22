package dyeablechicken.util;

import dyeablechicken.Main;

public class Logger {
    public static void log(String data) {
        System.out.println("[" + Main.MODID + "] " + data);
    }
}
