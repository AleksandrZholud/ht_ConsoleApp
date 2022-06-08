package consoleApp.exceptions;

import consoleApp.aspects.ConsoleColors;

public class DepartmentDoesNotExistException {
    public static String throwMessage() {
        return ConsoleColors.RED + "Department doesn't exist.\n";
    }
}
