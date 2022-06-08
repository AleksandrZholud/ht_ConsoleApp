package consoleApp.menu.menuItems;

import consoleApp.menu.MenuItem;
import consoleApp.menu.OutputMessage;
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
        OutputMessage.showMessage("Input your template");
        String template = in.next();
        OutputMessage.showMessage(searchService.globalSearch(template));
    }
}