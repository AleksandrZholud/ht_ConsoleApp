package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.menu.MenuItem;
import consoleApp.service.department.DepartmentFacade;

import java.util.Scanner;

public class AppointHeadOfDepartmentMenuItem extends MenuItem {

    private final DepartmentFacade departmentFacade;
    private final Scanner in;

    public AppointHeadOfDepartmentMenuItem(DepartmentFacade departmentFacade, Scanner in) {
        super("Appoint Head Of Department");
        this.departmentFacade = departmentFacade;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.soutInline("Input lector's full name ");
        String lectorFullName = in.next();
        OutputMessage.soutInline("Input name of Department ");
        String departmentName = in.next();
        departmentFacade.setHeadOfDepartment(lectorFullName, departmentName).showMessage();
    }
}
