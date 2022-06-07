package consoleApp.exceptions;

import consoleApp.aspects.CC;

public class DepartmentDoesNotExistException {
    public static String throwMessage() {
        return CC.RED + "Department doesn't exist.\n" + CC.RESET;
    }
}
