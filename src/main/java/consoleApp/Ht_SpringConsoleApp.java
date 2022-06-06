package consoleApp;

import consoleApp.aspects.CC;
import consoleApp.domain.DEGREE;
import consoleApp.domain.Department;
import consoleApp.domain.Lector;
import consoleApp.service.department.DepartmentFacade;
import consoleApp.service.lector.LectorFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        LOG.warn(CC.YELLOW_BOLD + "APPLICATION FINISHED" + CC.RESET);
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    Scanner in = new Scanner(System.in).useDelimiter("\n");

    @Override
    public void run(String... args) {
        LOG.warn(CC.YELLOW + "EXECUTING : command line runner" + CC.RESET);

        System.out.print(CC.YELLOW_BOLD + "\t\t\t\t\tInput your command, please: " + CC.RESET);
        for (String command = in.next(); !command.equalsIgnoreCase("exit"); command = in.next()) {
            command = command.toLowerCase();
            try {
                if (command.contains("who is head of department ")) {
                    String departmentName = command.substring("who is head of department ".length());
                    showHeadOfDepartment(departmentName);

                } else if (command.contains("show ") && command.contains(" statistics")) {
                    String departmentName = Arrays.stream(command.split(" "))
                            .filter(s -> !s.equals("show") && !s.equals("statistics")).collect(Collectors.joining(" "));
                    showStatistics(departmentName);

                } else if (command.contains("show the average salary for the department ")) {
                    String departmentName = command.substring("show the average salary for the department ".length());
                    showAverageSalaryByDepartmentName(departmentName);

                } else if (command.contains("show count of lectors for ")) {
                    String departmentName = command.substring("show count of lectors for ".length());
                    showCountOfLectorsByDepartmentName(departmentName);

                } else if (command.contains("global search by ")) {
                    String template = command.substring("global search by ".length());
                    globalSearch(template);

                } else if (command.contains("adDep ")) {
                    String lectorAndDepartment = command.substring("add lec into dep ".length());
                    addLectorIntoDepartment(lectorAndDepartment);

                } else if (command.contains("head ")) {
                    String lectorAndDepartment = command.substring("set head ".length());
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
    }

    private void addLectorIntoDepartment(String lectorAndDepartment) {
        String lectorFio = lectorAndDepartment.split(",")[0];
        String departmentName = lectorAndDepartment.split(",")[1];
        Lector lector = lectorFacade.findByFio(lectorFio);
        Department department = departmentFacade.findByName(departmentName);
        if (lector != null && department != null) {
            departmentFacade.addDepartmentToLector(lector.getId(), department);
        }
    }

    private void setHeadOfDepartment(String lectorAndDepartment) {
        String lectorFio = lectorAndDepartment.split(",")[0];
        String departmentName = lectorAndDepartment.split(",")[1];
        Lector lector = lectorFacade.findByFio(lectorFio);
        if (lector != null) {
            departmentFacade.setHeadOfDepartment(lector, departmentName);
            System.out.printf(CC.GREEN + "department %s.setHeadLector = OK\n" + CC.RESET, departmentName);
        }
    }

    private void globalSearch(String template) {
        List<String> collectionOfAllLectorFiosAndDepartmentNames = lectorFacade.getAllFios();
        collectionOfAllLectorFiosAndDepartmentNames.addAll(departmentFacade.getAllNames());
        collectionOfAllLectorFiosAndDepartmentNames = new ArrayList<>(collectionOfAllLectorFiosAndDepartmentNames.stream()
                .filter(anyDepNameOrFio -> anyDepNameOrFio.toLowerCase().contains(template))
                .collect(Collectors.toSet()));
        LOG.info(String.join(",", collectionOfAllLectorFiosAndDepartmentNames));
    }

    private void showCountOfLectorsByDepartmentName(String departmentName) {
        Department department = departmentFacade.findByName(departmentName);
        if (department != null) {
            if (department.getLectors() != null) {
                LOG.info(String.format("%s\n", department.getLectors().size()));
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName));
            }
        } else {
            showWarnTryFindDepartment(departmentName);
        }
    }

    private void showAverageSalaryByDepartmentName(String departmentName) {
        Department department = departmentFacade.findByName(departmentName);
        if (department != null) {
            if (department.getLectors() != null) {
                BigDecimal avgSalary = departmentFacade.getAverageSalaryByDepartmentId(department.getId());
                LOG.info(String.format("The average salary of %s is %s\n", departmentName, avgSalary.setScale(2, RoundingMode.HALF_UP)));
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName));
            }
        } else {
            showWarnTryFindDepartment(departmentName);
        }
    }

    private void showStatistics(String departmentName) {
        Department department = departmentFacade.findByName(departmentName);
        if (department != null) {
            if (department.getLectors() != null) {
                int countOfAssistants = departmentFacade.getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSISTANT);
                int countOfAssociateProfessors = departmentFacade.getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSOCIATE_PROFESSOR);
                int countOfProfessors = departmentFacade.getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.PROFESSOR);
                LOG.info(String.format("\n\t\t\tassistans - %s\n" +
                        "\t\t\tassociate professors - %s\n" +
                        "\t\t\tprofessors - %s\n", countOfAssistants, countOfAssociateProfessors, countOfProfessors));
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors" + CC.RESET, departmentName));
            }
        } else {
            showWarnTryFindDepartment(departmentName);
        }
    }

    private void showHeadOfDepartment(String departmentName) {
        Department department = departmentFacade.findByName(departmentName);
        if (department != null) {
            if (department.getName() != null) {
                LOG.info(String.format("Head of %s department is %s\n", departmentName, department.getHeadLector().getFio()));
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Head\n" + CC.RESET, departmentName));
            }
        } else {
            showWarnTryFindDepartment(departmentName);
        }
    }

    private void showWarnTryFindDepartment(String departmentName) {
        LOG.error(String.format(CC.RED + "Department %s doesn't exist.\n" + CC.RESET, departmentName));
    }

    private void fillDB(Scanner in) {
        LOG.info("   ======   Input string Names For DEPARTMENTS   ======  ");
        departmentFacade.fillDbDepartments(in.next().toLowerCase());
        LOG.info("   ======   Input string Names For LECTORS   ======   ");
        lectorFacade.fillDbLectors(in.next().toLowerCase());
    }
}