package consoleApp.menu.menuItems;

import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.MenuItem;
import consoleApp.UI.OutputMessage;
import consoleApp.service.department.DepartmentFacade;

import java.util.Scanner;

public class ShowHeadOfDepartmentMenuItem extends MenuItem {

    private final DepartmentFacade departmentFacade;
    private final Scanner in;

    public ShowHeadOfDepartmentMenuItem(DepartmentFacade departmentFacade, Scanner in) {
        super("Show Head Of Department");
        this.departmentFacade = departmentFacade;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.sout(ConsoleColors.YELLOW + "Input department name");
        String departmentName = in.next();
        departmentFacade.showHeadOfDepartment(departmentName).showMessage();
    }
}
