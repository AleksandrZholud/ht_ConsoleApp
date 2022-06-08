package consoleApp.menu.menuItems;

import consoleApp.menu.MenuItem;

public class SaveDepartmentMenuItem extends MenuItem {

    public SaveDepartmentMenuItem(String name) {
        super(name);
    }

    @Override
    public void exec() {

    }
//    private final AppService appService;
//    private final Scanner sc;
//
//    public SaveDepartmentMenuItem(AppService appService, Scanner sc) {
//
//        super("CREATE DEPARTMENT");
//        this.appService = appService;
//        this.sc = sc;
//    }
//
//    @Override
//    public void exec() {
//        try{
//            System.out.println("CREATE NAME FOR A NEW DEPARTMENT");
//            String input = sc.nextLine().toUpperCase();
//            appService.saveDepartment(new Department(input));
//            System.out.println(input + " DEPARTMENT SUCCESSFULLY SAVED");
//        } catch (Exception e) {
//            System.out.println("SOMETHING WENT WRONG, MAKE A REPORT");
//        }
//    }
}
