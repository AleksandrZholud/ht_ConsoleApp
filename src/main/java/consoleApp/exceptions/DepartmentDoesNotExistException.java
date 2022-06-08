package consoleApp.exceptions;

import consoleApp.aspects.ConsoleColors;

public class DepartmentDoesNotExistException {
    public static String generateMessage() {
        return ConsoleColors.RED + "Department doesn't exist.\n";
    }
}
