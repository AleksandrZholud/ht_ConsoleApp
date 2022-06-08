package consoleApp.menu;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.ConsoleColors;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public class OutputMessage {
    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    public static void showMessage(String message) {
        if (message.contains(ConsoleColors.RED) || message.contains(ConsoleColors.WHITE_BACK_AND_BLACK_BOLD)) {
            LOG.error(message + ConsoleColors.RESET);
        } else if (message.contains(ConsoleColors.YELLOW)) {
            LOG.warn(message + ConsoleColors.RESET);
        } else {
            LOG.info(message + ConsoleColors.RESET);
        }
    }
}