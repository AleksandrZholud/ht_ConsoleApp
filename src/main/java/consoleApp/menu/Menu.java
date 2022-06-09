package consoleApp.menu;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.menuItems.*;
import consoleApp.service.SearchService;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    public Menu(DepartmentFacade departmentFacade, SearchService searchService, LectorService lectorService) {
        items = new MenuItem[]{
                new ShowHeadOfDepartmentMenuItem(departmentFacade, in),
                new ShowStatByDepartmentMenuItem(departmentFacade, in),
                new ShowAverageSalaryMenuItem(departmentFacade, in),
                new ShowLectorsCountByDepartmentMenuItem(departmentFacade, in),
                new ShowEmployeesAndDepartmentsByTemplateMenuItem(searchService, in),
                //new SaveDepartmentMenuItem(departmentFacade, in),
                //new SaveLectorMenuItem(lectorService, in),
                //new AppointHeadOfDepartmentMenuItem(departmentFacade, in),
                new ExitingMenuItem()
        };
    }

    MenuItem[] items;
    Scanner in = new Scanner(System.in).useDelimiter("\n");

    private boolean isChoiceInvalid(int choice) {
        return choice < 0 || choice >= items.length;
    }


    private int getUserChoice() {
        OutputMessage.soutInline("Choose Menu item ");
        int choice = in.nextInt() - 1;
        in.nextLine();
        return choice;
    }

    private void showMenu() {
        OutputMessage.sout(ConsoleColors.YELLOW_BOLD + "- - - - - - - - - - - - - - - - - - - - - -\n\n");
        OutputMessage.sout(ConsoleColors.GREEN + "------------------MENU---------------------");
        for (int i = 0; i < items.length; i++) {
            OutputMessage.sout(ConsoleColors.GREEN + "|\t" + (i + 1) + ".\t" + items[i].getName());
        }
        OutputMessage.sout(ConsoleColors.GREEN + "-------------------------------------------\n");

    }

    public void run() {
        OutputMessage.showLoggedMessage(ConsoleColors.YELLOW_BOLD + "EXECUTING : command line runner");

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
    }

    @Override
    public void run(String... args) throws Exception {
        this.run();
    }
}