package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.menu.MenuItem;
import consoleApp.service.lector.LectorService;

import java.util.Scanner;

public class SaveLectorMenuItem extends MenuItem {

    private final LectorService lectorService;
    private final Scanner in;

    public SaveLectorMenuItem(LectorService lectorService, Scanner in) {
        super("Save new Lectors");
        this.lectorService = lectorService;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.soutInline("Input fullNames of Lectors");
        String lectorFullNameComaDepartmentName = in.next();
        lectorService.fillDbLectors(lectorFullNameComaDepartmentName).forEach(OutputMessage::showMessage);
    }
}
