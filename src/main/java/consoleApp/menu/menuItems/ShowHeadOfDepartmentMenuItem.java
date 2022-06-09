package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.menu.MenuItem;
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
        OutputMessage.soutInline("Input department name");
        String departmentName = in.next();
        departmentFacade.showHeadOfDepartment(departmentName).showMessage();
    }
}
