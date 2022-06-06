package consoleApp.service.department;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.CC;
import consoleApp.domain.DEGREE;
import consoleApp.domain.Department;
import consoleApp.domain.Lector;
import consoleApp.service.lector.LectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class DepartmentFacade {

    private final DepartmentService departmentService;
    private final LectorService lectorService;

    public DepartmentFacade(DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(Ht_SpringConsoleApp.class);

    private void showErrorOfFindDepartment(String departmentName) {
        LOG.error(String.format(CC.RED + "Department %s doesn't exist.\n" + CC.RESET, departmentName));
    }

    public Department findByName(String departmentName) {
        return departmentService.findByName(departmentName).orElse(null);
    }

    public void setHeadOfDepartment(Lector lector, String departmentName) {
        lector = addDepartmentToLector(lector.getId(),
                Objects.requireNonNull(departmentService.findByName(departmentName).orElse(null)));
        Department department = departmentService.findByName(departmentName).orElse(null);
        if (department != null) {
            department.setHeadLector(lector);
            departmentService.save(department);
            System.out.println(CC.GREEN + "setHeadOfDepartment OK" + CC.RESET);
        }
    }

    public Lector addDepartmentToLector(Long lectorId, Department department) {
        department.addLector(lectorId);
        departmentService.save(department);
        System.out.println(CC.GREEN + "addLectorIntoDepartment OK" + CC.RESET);
        return lectorService.findById(lectorId).orElse(null);
    }

    public void fillDbDepartments(String namesSeparatedByComa) {
        if (!Objects.equals(namesSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(namesSeparatedByComa.split(",")));
            fios.forEach(departmentName -> {
                try {
                    departmentService.save(new Department(departmentName));
                    System.out.printf(CC.GREEN + "Department: %s saved." + CC.RESET + "\n", departmentName);
                } catch (Exception e) {
                    System.out.printf(CC.RED + "\t\t\t\t\t\t\t\t\tDepartment %s exist in DB\n" + CC.RESET, departmentName);
                }
            });
        }
    }

    public int getCountOfLectorsByDepartmentIdAndDegree(Long id, DEGREE degree) {
        return departmentService.getCountOfLectorsByDepartmentIdAndDegree(id, degree);
    }

    public BigDecimal getAverageSalaryByDepartmentId(Long id) {
        return departmentService.getAverageSalaryByDepartmentId(id);
    }

    public List<String> getAllNames() {
        return departmentService.getAllNames();
    }

    public void showCountOfLectorsByDepartmentName(String departmentName) {
        Optional<Department> optDepartment = departmentService.findByName(departmentName);
        if (optDepartment.isPresent()) {
            Department department = optDepartment.get();
            if (department.getLectors() != null) {
                System.out.printf("%s\n", department.getLectors().size());
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName));
            }
        } else {
            showErrorOfFindDepartment(departmentName);
        }
    }

    public void showHeadOfDepartment(String departmentName) {
        Optional<Department> optDepartment = departmentService.findByName(departmentName);
        if (optDepartment.isPresent()) {
            Department department = optDepartment.get();
            if (department.getName() != null) {
                System.out.printf("Head of %s department is %s\n", departmentName, department.getHeadLector().getFullName());
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Head\n" + CC.RESET, departmentName));
            }
        } else {
            showErrorOfFindDepartment(departmentName);
        }
    }

    public void showStatistics(String departmentName) {
        Optional<Department> optDepartment = departmentService.findByName(departmentName);
        if (optDepartment.isPresent()) {
            Department department = optDepartment.get();
            if (department.getLectors() != null) {
                int countOfAssistants = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSISTANT);
                int countOfAssociateProfessors = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSOCIATE_PROFESSOR);
                int countOfProfessors = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.PROFESSOR);
                System.out.printf("assistants - %s\n" +
                        "associate professors - %s\n" +
                        "professors - %s\n", countOfAssistants, countOfAssociateProfessors, countOfProfessors);
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors" + CC.RESET, departmentName));
            }
        } else {
            showErrorOfFindDepartment(departmentName);
        }
    }

    public void showAverageSalaryByDepartmentName(String departmentName) {
        Optional<Department> optDepartment = departmentService.findByName(departmentName);
        if (optDepartment.isPresent()) {
            Department department = optDepartment.get();
            if (department.getLectors() != null) {
                BigDecimal avgSalary = getAverageSalaryByDepartmentId(department.getId());
                System.out.printf("The average salary of %s is %s\n", departmentName, avgSalary.setScale(2, RoundingMode.HALF_UP));
            } else {
                LOG.warn(String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName));
            }
        } else {
            showErrorOfFindDepartment(departmentName);
        }
    }
}
