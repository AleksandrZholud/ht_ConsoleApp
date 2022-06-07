package consoleApp.service.department;

import consoleApp.Ht_SpringConsoleApp;
import consoleApp.aspects.CC;
import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Department;
import consoleApp.domain.model.Lector;
import consoleApp.exceptions.DepartmentDoesNotExistException;
import consoleApp.service.lector.LectorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentFacade {

    private final DepartmentService departmentService;
    private final LectorService lectorService;

    public DepartmentFacade(DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
    }

    public Department findByName(String departmentName) {
        return departmentService.findByName(departmentName).orElse(null);
    }

    public void setHeadOfDepartment(Long lectorId, String departmentName) {

        Lector finalLector = addDepartmentToLector(lectorId,
                Objects.requireNonNull(departmentService.findByName(departmentName).orElse(null)));

        departmentService.findByName(departmentName).map(department -> {
            department.setHeadLector(finalLector);
            departmentService.save(department);
            System.out.println(CC.GREEN + "setHeadOfDepartment OK" + CC.RESET);
            return null;
        });
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
                    System.out.printf(CC.YELLOW + CC.TABS + "Department %s exist in DB\n" + CC.RESET, departmentName);
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

    public String showCountOfLectorsByDepartmentName(String departmentName) {
        return departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfSizeLectorList(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::throwMessage);
    }

    private String getOutputStringOfSizeLectorList(String departmentName, Department department) {
        if (department.getLectors() != null) {
            return String.format("%s\n", department.getLectors().size());
        } else {
            return String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName);
        }
    }

    public String showHeadOfDepartment(String departmentName) {
        return departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfHead(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::throwMessage);
    }

    private String getOutputStringOfHead(String departmentName, Department department) {
        if (department.getName() != null) {
            return String.format("Head of %s department is %s\n", departmentName, department.getHeadLector().getFullName());
        } else {
            return String.format(CC.YELLOW + "Department %s has no Head\n" + CC.RESET, departmentName);
        }
    }

    public String showStatistics(String departmentName) {
        return departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfStatistics(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::throwMessage);
    }

    private String getOutputStringOfStatistics(String departmentName, Department department) {
        if (department.getLectors() != null) {
            int countOfAssistants = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSISTANT);
            int countOfAssociateProfessors = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.ASSOCIATE_PROFESSOR);
            int countOfProfessors = getCountOfLectorsByDepartmentIdAndDegree(department.getId(), DEGREE.PROFESSOR);
            return String.format("assistants - %s\n" +
                    "associate professors - %s\n" +
                    "professors - %s\n", countOfAssistants, countOfAssociateProfessors, countOfProfessors);
        } else {
            return String.format(CC.YELLOW + "Department %s has no Lectors" + CC.RESET, departmentName);
        }
    }

    public String showAverageSalaryByDepartmentName(String departmentName) {
        return departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfAverageSalary(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::throwMessage);
    }

    private String getOutputStringOfAverageSalary(String departmentName, Department department) {
        if (department.getLectors() != null) {
            BigDecimal avgSalary = getAverageSalaryByDepartmentId(department.getId());
            return String.format("The average salary of %s is %s\n", departmentName, avgSalary.setScale(2, RoundingMode.HALF_UP));
        } else {
            return String.format(CC.YELLOW + "Department %s has no Lectors\n" + CC.RESET, departmentName);
        }
    }
}