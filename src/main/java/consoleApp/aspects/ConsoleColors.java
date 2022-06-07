package consoleApp.aspects;

//ConsoleColors
public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW

    // Bold
    private static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW

    // Background
    private static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static final String WHITE_BACK_AND_BLACK_BOLD = WHITE_BACKGROUND_BRIGHT + BLACK_BOLD;

    public static final String TABS = "\t\t\t\t\t\t\t\t\t";
}
