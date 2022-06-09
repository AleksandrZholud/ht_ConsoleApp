package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.menu.MenuItem;
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
        OutputMessage.sout("Input department name");
        String departmentName = in.next();
        departmentFacade.showStatistics(departmentName).showMessage();
    }
}
