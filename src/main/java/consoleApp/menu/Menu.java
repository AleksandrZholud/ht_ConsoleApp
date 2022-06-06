package consoleApp.menu;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.CC;
import consoleApp.domain.Department;
import consoleApp.domain.Lector;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    private final DepartmentFacade departmentFacade;
    private final LectorFacade lectorFacade;

    public Menu(DepartmentFacade departmentFacade, LectorFacade lectorFacade) {
        this.departmentFacade = departmentFacade;
        this.lectorFacade = lectorFacade;
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    Scanner in = new Scanner(System.in).useDelimiter("\n");

    public void run(){
        LOG.warn(CC.YELLOW + "EXECUTING : command line runner" + CC.RESET);

        System.out.print(CC.YELLOW_BOLD + "\t\t\t\t\tInput your command, please: " + CC.RESET);
        for (String command = in.next(); !command.equalsIgnoreCase("exit"); command = in.next()) {
            command = command.toLowerCase();
            try {
                if (command.contains("who is head of department ")) {
                    String departmentName = command.substring("who is head of department ".length());
                    departmentFacade.showHeadOfDepartment(departmentName);

                } else if (command.contains("show ") && command.contains(" statistics")) {
                    String departmentName = Arrays.stream(command.split(" "))
                            .filter(s -> !s.equals("show") && !s.equals("statistics")).collect(Collectors.joining(" "));
                    departmentFacade.showStatistics(departmentName);

                } else if (command.contains("show the average salary for the department ")) {
                    String departmentName = command.substring("show the average salary for the department ".length());
                    departmentFacade.showAverageSalaryByDepartmentName(departmentName);

                } else if (command.contains("show count of lectors for ")) {
                    String departmentName = command.substring("show count of lectors for ".length());
                    departmentFacade.showCountOfLectorsByDepartmentName(departmentName);

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
                    LOG.error(CC.RED + "ERROR: Unknown command!" + CC.RESET);
                }
            } catch (Exception e) {
                LOG.error(CC.WHITE_BACK_AND_BLACK_BOLD + "Application error! Something happened wrong!" + CC.RESET);
            }
            System.out.print(CC.YELLOW_BOLD + "\t\t\t\t\tInput your command, please: " + CC.RESET);
        }

        LOG.warn(CC.YELLOW_BOLD + "APPLICATION FINISHED" + CC.RESET);
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
            departmentFacade.setHeadOfDepartment(lector, departmentName);
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
        LOG.info("   ======   Input string Names For DEPARTMENTS   ======  ");
        departmentFacade.fillDbDepartments(in.next().toLowerCase());
        LOG.info("   ======   Input string Names For LECTORS   ======   ");
        lectorFacade.fillDbLectors(in.next().toLowerCase());
    }
}
