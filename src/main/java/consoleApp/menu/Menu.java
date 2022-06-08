package consoleApp.menu;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.menuItems.*;
import consoleApp.service.SearchService;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    private final DepartmentFacade departmentFacade;
    private final LectorFacade lectorFacade;
    private final SearchService searchService;

    public Menu(DepartmentFacade departmentFacade, LectorFacade lectorFacade, SearchService searchService) {
        this.departmentFacade = departmentFacade;
        this.lectorFacade = lectorFacade;
        this.searchService = searchService;
        items = new MenuItem[]{
                new ShowHeadOfDepartmentMenuItem(departmentFacade, in),
                new ShowStatByDepartmentMenuItem(departmentFacade, in),
                new ShowAverageSalaryMenuItem(departmentFacade, in),
                new ShowLectorsCountByDepartmentMenuItem(departmentFacade, in),
                new ShowEmployeesAndDepartmentsByTemplateMenuItem(searchService, in),
                new ExitingMenuItem()
                //new SaveDepartmentMenuItem(in),
                //new SaveLectorMenuItem(in),
                //new AppointHeadOfDepartmentMenuItem(in),
        };
    }

    MenuItem[] items;
    Scanner in = new Scanner(System.in).useDelimiter("\n");

    private boolean isChoiceInvalid(int choice) {
        return choice < 0 || choice >= items.length;
    }


    private int getUserChoice() {
        OutputMessage.showLoggedMessage(ConsoleColors.YELLOW + ConsoleColors.YELLOW_BOLD + "Choose Menu item");
        int choice = in.nextInt() - 1;
        in.nextLine();
        return choice;
    }

    private void showMenu() {
        OutputMessage.sout("-------------MENU----------------");
        for (int i = 0; i < items.length; i++) {
            OutputMessage.sout(i + 1 + ". " + items[i].getName());
        }
        OutputMessage.sout("---------------------------------");

    }

    public void run() {
        OutputMessage.showLoggedMessage(ConsoleColors.YELLOW + "EXECUTING : command line runner");

        while (true) {
            showMenu();
            int choice;
            try {
                choice = getUserChoice();
            } catch (InputMismatchException e) {
                OutputMessage.showLoggedMessage(ConsoleColors.WHITE_BACK_AND_BLACK_BOLD + "Input should be a number!");
                in.nextLine();
                continue;
            }

            if (isChoiceInvalid(choice)) {
                OutputMessage.sout(ConsoleColors.WHITE_BACK_AND_BLACK_BOLD + "Try again!");
                continue;
            }
            items[choice].exec();
            if (items[choice].closeAfter()) break;
        }

        OutputMessage.showLoggedMessage(ConsoleColors.YELLOW_BOLD + "APPLICATION FINISHED");

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

           /*     } else if (command.contains("add ")) {
                    String lectorAndDepartment = command.substring("add ".length());
                    addLectorIntoDepartment(lectorAndDepartment);

                } else if (command.contains("head ")) {
                    String lectorAndDepartment = command.substring("head ".length());
                    setHeadOfDepartment(lectorAndDepartment);

                } else if (command.equals("fill")) {
                    fillDB(in);

              */

    }

//
//    private void fillDB(Scanner in) {
//        OutputMessage.showMessage("   ======   Input string Names For DEPARTMENTS   ======  ");
//        departmentFacade.fillDbDepartments(in.next().toLowerCase());
//        OutputMessage.showMessage("   ======   Input string Names For LECTORS   ======   ");
//        lectorFacade.fillDbLectors(in.next().toLowerCase());
//    }

    @Override
    public void run(String... args) throws Exception {
        this.run();
    }
}