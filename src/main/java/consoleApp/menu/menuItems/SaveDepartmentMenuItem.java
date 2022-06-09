package consoleApp.menu.menuItems;

import consoleApp.UI.OutputMessage;
import consoleApp.menu.MenuItem;
import consoleApp.service.department.DepartmentFacade;

import java.util.Scanner;

public class SaveDepartmentMenuItem extends MenuItem {

    private final DepartmentFacade departmentFacade;
    private final Scanner in;

    public SaveDepartmentMenuItem(DepartmentFacade departmentFacade, Scanner in) {
        super("Save new Departments");
        this.departmentFacade = departmentFacade;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.soutInline("Input names of Departments");
        String lectorFullNameComaDepartmentName = in.next();
        departmentFacade.fillDbDepartments(lectorFullNameComaDepartmentName).forEach(OutputMessage::showMessage);
    }
}
