package consoleApp.menu.menuItems;

import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.MenuItem;
import consoleApp.UI.OutputMessage;

public class ExitingMenuItem extends MenuItem {
    public ExitingMenuItem() {
        super("EXIT", true);
    }

    @Override
    public void exec() {
        OutputMessage.sout(ConsoleColors.YELLOW + "BYE BYE! " + ConsoleColors.RESET);
    }
}
