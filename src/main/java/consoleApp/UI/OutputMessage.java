package consoleApp.UI;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.ConsoleColors;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public class OutputMessage {
    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    private String message;

    public OutputMessage(String message) {
        this.message = message;
    }

    public static void showLoggedMessage(String message) {
        if (message.contains(ConsoleColors.RED) || message.contains(ConsoleColors.WHITE_BACK_AND_BLACK_BOLD)) {
            LOG.error(message + ConsoleColors.RESET);
        } else if (message.contains(ConsoleColors.YELLOW)) {
            LOG.warn(message + ConsoleColors.RESET);
        } else {
            LOG.info(message + ConsoleColors.RESET);
        }
    }

    public static void sout(String message) {
        System.out.println(message + ConsoleColors.RESET);
    }

    public static void soutInline(String message) {
        System.out.print(ConsoleColors.YELLOW + message + ConsoleColors.RESET);
    }

    public void showMessage() {
        System.out.println(ConsoleColors.YELLOW + "Output:\t" + ConsoleColors.RESET + message + ConsoleColors.RESET);
    }
}