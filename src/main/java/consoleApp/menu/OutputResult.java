package consoleApp.menu;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.ConsoleColors;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public class OutputResult {
    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    public static void showResult(String message) {
        if (message.contains(ConsoleColors.RED)) {
            LOG.error(message);
        } else if (message.contains(ConsoleColors.YELLOW)) {
            LOG.warn(message);
        } else {
            LOG.info(message);
        }
    }
}