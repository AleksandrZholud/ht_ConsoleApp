package consoleApp.service.department;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Department;
import consoleApp.domain.model.Lector;
import consoleApp.exceptions.DepartmentDoesNotExistException;
import consoleApp.service.Validate;
import consoleApp.service.lector.LectorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public int getCountOfLectorsByDepartmentIdAndDegree(Long id, DEGREE degree) {
        return departmentService.getCountOfLectorsByDepartmentIdAndDegree(id, degree);
    }

    public BigDecimal getAverageSalaryByDepartmentId(Long id) {
        return departmentService.getAverageSalaryByDepartmentId(id);
    }

    public List<String> getAllNames() {
        return departmentService.getAllNames();
    }

    public OutputMessage showCountOfLectorsByDepartmentName(String departmentName) {
        return new OutputMessage(departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfSizeLectorList(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::generateMessage));
    }

    public OutputMessage showHeadOfDepartment(String departmentName) {
        return new OutputMessage(departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfHead(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::generateMessage));
    }

    public OutputMessage showStatistics(String departmentName) {
        return new OutputMessage(departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfStatistics(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::generateMessage));
    }

    public OutputMessage showAverageSalaryByDepartmentName(String departmentName) {
        return new OutputMessage(departmentService.findByName(departmentName)
                .map(department -> getOutputStringOfAverageSalary(departmentName, department))
                .orElseGet(DepartmentDoesNotExistException::generateMessage));
    }

    public List<OutputMessage> fillDbDepartments(String namesSeparatedByComa) {
        List<OutputMessage> outputMessageList = new ArrayList<>();
        if (!namesSeparatedByComa.isEmpty()) {
            List<String> names = new ArrayList<>(Arrays.asList(namesSeparatedByComa.split(",")));
            names.stream().filter(Validate::validateInputByComa).forEach(departmentName -> {
                try {
                    Department department = departmentService.findByName(departmentName).orElse(null);
                    if (department != null) {
                        outputMessageList.add(new OutputMessage(String.format(ConsoleColors.YELLOW + "Department %s exists in DB\n", departmentName)));
                    } else {
                        departmentService.save(new Department(departmentName));
                        outputMessageList.add(new OutputMessage(String.format(ConsoleColors.GREEN + "Department: %s saved.\n", departmentName)));
                    }
                } catch (Exception e) {
                    outputMessageList.add(new OutputMessage(ConsoleColors.RED + "Error: Department name must be between 3 and 40 characters long.\n"));
                }
            });
        } else {
            outputMessageList.add(new OutputMessage(ConsoleColors.YELLOW + "Empty input"));
        }
        return outputMessageList;
    }

    public OutputMessage setHeadOfDepartment(String lectorFullName, String departmentName) {
        Department department = departmentService.findByName(departmentName).orElse(null);
        if (department == null) {
            return new OutputMessage(ConsoleColors.RED + "department " + departmentName + " is not exist in DB");
        } else {
            return lectorService.findByFullName(lectorFullName)
                    .map(lector -> {
                        Lector finalLector = addDepartmentToLector(lector.getId(), department);
                        departmentService.findByName(departmentName)
                                .map(updatedDepartment -> {
                                    updatedDepartment.setHeadLector(finalLector);
                                    return departmentService.save(updatedDepartment);
                                });
                        return new OutputMessage(ConsoleColors.GREEN + "head updated");
                    }).orElse(new OutputMessage(ConsoleColors.RED + "lector " + lectorFullName + " is not exist in DB"));
        }
    }

    private String getOutputStringOfAverageSalary(String departmentName, Department department) {
        if (department.getLectors() != null) {
            BigDecimal avgSalary = getAverageSalaryByDepartmentId(department.getId());
            return String.format("The average salary of %s is %s\n", departmentName, avgSalary.setScale(2, RoundingMode.HALF_UP));
        } else {
            return String.format(ConsoleColors.YELLOW + "Department %s has no Lectors\n", departmentName);
        }
    }

    private Lector addDepartmentToLector(Long lectorId, Department department) {
        department.addLector(lectorId);
        departmentService.save(department);
        return lectorService.findById(lectorId).orElse(null);
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
            return String.format(ConsoleColors.YELLOW + "Department %s has no Lectors", departmentName);
        }
    }

    private String getOutputStringOfSizeLectorList(String departmentName, Department department) {
        if (department.getLectors() != null) {
            return String.format("%s\n", department.getLectors().size());
        } else {
            return String.format(ConsoleColors.YELLOW + "Department %s has no Lectors\n", departmentName);
        }
    }

    private String getOutputStringOfHead(String departmentName, Department department) {
        if (department.getName() != null) {
            try {
                String headOfDepartment = department.getHeadLector().getFullName();
                return String.format("Head of %s department is %s\n", departmentName, headOfDepartment);
            } catch (Exception e) {
                return String.format(ConsoleColors.YELLOW + "Department %s has no Head\n", departmentName);
            }
        } else {
            return DepartmentDoesNotExistException.generateMessage();
        }
    }
}