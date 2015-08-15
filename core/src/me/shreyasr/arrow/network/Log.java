package me.shreyasr.arrow.network;

public class Log {

    public static void exception(Throwable e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
    }
}
