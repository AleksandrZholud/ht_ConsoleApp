package consoleApp.menu.menuItems;

import consoleApp.menu.MenuItem;
import consoleApp.UI.OutputMessage;
import consoleApp.service.department.DepartmentFacade;

import java.util.Scanner;

public class ShowAverageSalaryMenuItem extends MenuItem {

    private final DepartmentFacade departmentFacade;
    private final Scanner in;

    public ShowAverageSalaryMenuItem(DepartmentFacade departmentFacade, Scanner in) {
        super("Show Average Salary By Department");
        this.departmentFacade = departmentFacade;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.sout("Input department name");
        String departmentName = in.next();
        departmentFacade.showAverageSalaryByDepartmentName(departmentName).showMessage();
    }
}