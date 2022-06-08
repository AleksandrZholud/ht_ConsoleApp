package consoleApp.menu.menuItems;

import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.MenuItem;
import consoleApp.menu.OutputMessage;

public class ExitingMenuItem extends MenuItem {
    public ExitingMenuItem() {
        super("EXIT", true);
    }

    @Override
    public void exec() {
        OutputMessage.showMessage(ConsoleColors.YELLOW + "BYE BYE! \r\n" + ConsoleColors.RESET);
    }
}
