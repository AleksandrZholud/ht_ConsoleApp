package consoleApp.menu;

import consoleApp.aspects.ConsoleColors;
import consoleApp.domain.model.Department;
import consoleApp.domain.model.Lector;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu implements CommandLineRunner {

    private final DepartmentFacade departmentFacade;
    private final LectorFacade lectorFacade;

    public Menu(DepartmentFacade departmentFacade, LectorFacade lectorFacade) {
        this.departmentFacade = departmentFacade;
        this.lectorFacade = lectorFacade;
    }

    Scanner in = new Scanner(System.in).useDelimiter("\n");

    public void run() {
        OutputResult.showResult(ConsoleColors.YELLOW + "EXECUTING : command line runner" + ConsoleColors.RESET);
        OutputResult.showResult(ConsoleColors.YELLOW_BOLD + ConsoleColors.TABS + "Input your command, please: " + ConsoleColors.RESET);

        for (String command = in.next(); !command.equalsIgnoreCase("exit"); command = in.next()) {
            command = command.toLowerCase();
            try {
                if (command.contains("who is head of department ")) {
                    String departmentName = command.substring("who is head of department ".length());
                    System.out.println(departmentFacade.showHeadOfDepartment(departmentName));

                } else if (command.contains("show ") && command.contains(" statistics")) {
                    String departmentName = Arrays.stream(command.split(" "))
                            .filter(s -> !s.equals("show") && !s.equals("statistics")).collect(Collectors.joining(" "));
                    System.out.println(departmentFacade.showStatistics(departmentName));

                } else if (command.contains("show the average salary for the department ")) {
                    String departmentName = command.substring("show the average salary for the department ".length());
                    System.out.println(departmentFacade.showAverageSalaryByDepartmentName(departmentName));

                } else if (command.contains("show count of lectors for ")) {
                    String departmentName = command.substring("show count of lectors for ".length());
                    System.out.println(departmentFacade.showCountOfLectorsByDepartmentName(departmentName));

                } else if (command.contains("global search by ")) {
                    String template = command.substring("global search by ".length());
                    globalSearch(template);

                } else if (command.contains("add ")) {
                    String lectorAndDepartment = command.substring("add ".length());
                    addLectorIntoDepartment(lectorAndDepartment);

                } else if (command.contains("head ")) {
                    String lectorAndDepartment = command.substring("head ".length());
                    setHeadOfDepartment(lectorAndDepartment);

                } else if (command.equals("fill")) {
                    fillDB(in);

                } else {
                    OutputResult.showResult(ConsoleColors.RED + "ERROR: Unknown command!" + ConsoleColors.RESET);
                }
            } catch (Exception e) {
                OutputResult.showResult(ConsoleColors.WHITE_BACK_AND_BLACK_BOLD + "Application error! Something happened wrong!" + ConsoleColors.RESET);
            }
            OutputResult.showResult(ConsoleColors.YELLOW_BOLD + ConsoleColors.TABS + "Input your command, please: " + ConsoleColors.RESET);
        }

        OutputResult.showResult(ConsoleColors.YELLOW_BOLD + "APPLICATION FINISHED" + ConsoleColors.RESET);
    }

    private void addLectorIntoDepartment(String lectorAndDepartment) {
        String lectorFullName = lectorAndDepartment.split(",")[0];
        String departmentName = lectorAndDepartment.split(",")[1];
        Lector lector = lectorFacade.findByFullName(lectorFullName);
        Department department = departmentFacade.findByName(departmentName);
        if (lector != null && department != null) {
            departmentFacade.addDepartmentToLector(lector.getId(), department);
        }
    }

    private void setHeadOfDepartment(String lectorAndDepartment) {
        String lectorFullName = lectorAndDepartment.split(",")[0];
        String departmentName = lectorAndDepartment.split(",")[1];
        Lector lector = lectorFacade.findByFullName(lectorFullName);
        if (lector != null) {
            departmentFacade.setHeadOfDepartment(lector.getId(), departmentName);
        }
    }

    private void globalSearch(String template) {
        List<String> collectionOfAllLectorFullNamesAndDepartmentNames = lectorFacade.getAllFullNames();
        collectionOfAllLectorFullNamesAndDepartmentNames.addAll(departmentFacade.getAllNames());
        collectionOfAllLectorFullNamesAndDepartmentNames = new ArrayList<>(collectionOfAllLectorFullNamesAndDepartmentNames.stream()
                .filter(anyDepNameOrFio -> anyDepNameOrFio.toLowerCase().contains(template))
                .collect(Collectors.toSet()));
        System.out.println(String.join(",", collectionOfAllLectorFullNamesAndDepartmentNames));
    }

    private void fillDB(Scanner in) {
        OutputResult.showResult("   ======   Input string Names For DEPARTMENTS   ======  ");
        departmentFacade.fillDbDepartments(in.next().toLowerCase());
        OutputResult.showResult("   ======   Input string Names For LECTORS   ======   ");
        lectorFacade.fillDbLectors(in.next().toLowerCase());
    }

    @Override
    public void run(String... args) throws Exception {
        this.run();
    }
}