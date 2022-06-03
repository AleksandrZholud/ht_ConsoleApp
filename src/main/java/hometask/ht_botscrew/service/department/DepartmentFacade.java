package hometask.ht_botscrew.service.department;

import hometask.ht_botscrew.domain.DEGREE;
import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.service.lector.LectorFacade;
import org.springframework.stereotype.Service;

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

    public Department setHeadOfDepartment(Lector lector, String departmentName) {
        lector = addDepartmentToLector(lector.getId(),
                Objects.requireNonNull(departmentService.findByName(departmentName).orElse(null)));
        Department department = departmentService.findByName(departmentName).orElse(null);
        if (department != null) {
            department.setHeadLector(lector);
            System.out.printf("department{%s}.setHeadLector = OK\n", departmentName);
            return departmentService.save(department);
        } else return null;
    }

    public Lector addDepartmentToLector(Long lectorId, Department department) {
        department.addLector(lectorId);
        departmentService.save(department);
        System.out.println("addLectorIntoDepartment OK");
        return lectorFacade.findById(lectorId);
    }

    public int getCountOfLectors(String nameDepartment) {
        return departmentService.getCountOfLectors(nameDepartment);
    }

    public void fillDbDepartments(String namesSeparatedByComa) {
        if (!Objects.equals(namesSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(namesSeparatedByComa.split(",")));
            fios.forEach(departmentName -> {
                departmentService.save(new Department(departmentName));
                System.out.println(String.format("Department: %s saved.", departmentName));
            });
        }
    }

    public int getCountOfLectorsByDepartmentIdAndDegree(Long id, DEGREE degree) {
        return departmentService.getCountOfLectorsByDepartmentIdAndDegree(id, degree);
    }

    public long getAvarageSalaryByDepartmentId(Long id) {
        return departmentService.getAvarageSalaryByDepartmentId(id);
    }

    public List<String> getAllNames() {
        return departmentService.getAllNames();
    }
}
