package consoleApp.service.department;

import consoleApp.aspects.CC;
import consoleApp.domain.DEGREE;
import consoleApp.domain.Department;
import consoleApp.domain.Lector;
import consoleApp.service.lector.LectorFacade;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;

@Service
public class DepartmentFacade {

    private final DepartmentService departmentService;
    private final LectorFacade lectorFacade;

    public DepartmentFacade(DepartmentService departmentService, LectorFacade lectorFacade) {
        this.departmentService = departmentService;
        this.lectorFacade = lectorFacade;
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
}
