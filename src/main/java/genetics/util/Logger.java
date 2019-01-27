package genetics.util;

import genetics.Main;

public class Logger {
    public static boolean debugMode = false;
    public static void log(String data) {
        System.out.println("[" + Main.MODID + "] " + data);
    }
    public static void debugLog(String data) { if (debugMode) System.out.println("[" + Main.MODID + "] DEBUG: " + data);}
}
