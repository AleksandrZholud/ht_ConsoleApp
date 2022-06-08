package consoleApp.menu.menuItems;

import consoleApp.aspects.ConsoleColors;
import consoleApp.menu.MenuItem;
import consoleApp.UI.OutputMessage;
import consoleApp.service.SearchService;

import java.util.Scanner;

public class ShowEmployeesAndDepartmentsByTemplateMenuItem extends MenuItem {

    private final SearchService searchService;
    private final Scanner in;

    public ShowEmployeesAndDepartmentsByTemplateMenuItem(SearchService searchService, Scanner in) {
        super("Global search by template");
        this.searchService = searchService;
        this.in = in;
    }

    @Override
    public void exec() {
        OutputMessage.sout(ConsoleColors.YELLOW + "Input your template");
        String template = in.next();
        searchService.globalSearch(template).showMessage();
    }
}