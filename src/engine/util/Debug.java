package engine.util;

public class Debug {
    private static final boolean DISPLAY_MESSAGE_LOG = true;

    public static void assertError(boolean condition, String errorMessage) {
        if(!condition) {
            System.err.println(errorMessage);
            System.exit(1);
        }
    }

    public static void log(String text) {
        if(DISPLAY_MESSAGE_LOG)
            System.out.println(text);
    }

    public static void log(Class referrer, String text) {
        if(DISPLAY_MESSAGE_LOG) {
            System.out.print(referrer.getCanonicalName() + ": ");
            System.out.println(text);
        }
    }
}
