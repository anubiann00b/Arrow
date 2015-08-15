package me.shreyasr.arrow;

public class Log {

    public static void exception(Throwable e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
    }
}
