package consoleApp.menu.menuItems;

import consoleApp.menu.MenuItem;
import consoleApp.menu.OutputMessage;
import consoleApp.service.department.DepartmentFacade;

import java.util.Scanner;

public class ShowStatByDepartmentMenuItem extends MenuItem {

    private final DepartmentFacade departmentFacade;
    private final Scanner in;

    public ShowStatByDepartmentMenuItem(DepartmentFacade departmentFacade, Scanner in) {
        super("Show Statistics By Department");
        this.departmentFacade = departmentFacade;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.showMessage("Input department name");
        String departmentName = in.next();
        OutputMessage.showMessage(departmentFacade.showStatistics(departmentName));
    }
}
