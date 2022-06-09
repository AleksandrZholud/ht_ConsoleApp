package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.MenuItem;

public class ExitingMenuItem extends MenuItem {
    public ExitingMenuItem() {
        super("EXIT", true);
    }

    @Override
    public void exec() {
        OutputMessage.sout(ConsoleColors.YELLOW + "BYE BYE!");
    }
}
