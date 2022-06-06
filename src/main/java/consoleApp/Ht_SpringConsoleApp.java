package consoleApp;

import consoleApp.menu.Menu;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ht_SpringConsoleApp implements CommandLineRunner {


    private final DepartmentFacade departmentFacade;
    private final LectorFacade lectorFacade;

    public Ht_SpringConsoleApp(DepartmentFacade departmentFacade, LectorFacade lectorFacade) {
        this.departmentFacade = departmentFacade;
        this.lectorFacade = lectorFacade;
    }

    public static void main(String[] args) {
        SpringApplication.run(Ht_SpringConsoleApp.class, args);
    }

    Menu m = new Menu(departmentFacade, lectorFacade);

    @Override
    public void run(String... args) {
        m.run();
    }
}