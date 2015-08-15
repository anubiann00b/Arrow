package me.shreyasr.arrow;

public class Log {

    public static void exception(Throwable e) {
        System.out.println("Exception: " + e.getMessage());
        e.printStackTrace();
    }

    public static void error(Throwable e) {
        System.out.println(e.getMessage());
    }

    public static void m(String s) {
        System.out.println(s);
    }
}
