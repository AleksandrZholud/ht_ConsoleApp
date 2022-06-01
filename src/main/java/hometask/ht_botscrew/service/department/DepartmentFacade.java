package hometask.ht_botscrew.service.department;

import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.service.lector.LectorFacade;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentFacade {

    private final DepartmentService departmentService;
    private final LectorFacade lectorFacade;

    public DepartmentFacade(LectorFacade lectorFacade, DepartmentService departmentService) {
        this.lectorFacade = lectorFacade;
        this.departmentService = departmentService;
    }

    public Department findByName(String departmentName) {
        return departmentService.findByName(departmentName).orElse(null);
    }

    public Department setHeadOfDepartment(Lector lector, String departmentName) {
        Department department = departmentService.findByName(departmentName).orElse(null);
        addDepartmentToLector_ifLectorNotContainsInCurrentDepartment(lector, department);
        department.setHeadLector(lector);
        System.out.printf("\ndepartment{%s}.setHeadLector = OK\n\n", departmentName);
        return departmentService.save(department);
    }

    private void addDepartmentToLector_ifLectorNotContainsInCurrentDepartment(Lector lector, Department department) {
        List<Lector> lectors = lectorFacade.findAllLectorsByDepartmentId(department.getId());
        lectors.stream().filter(v -> !v.getName().equals(lector.getName()))
                .forEach(unsavedLectorInDepartment -> lectorFacade.addDepartmentForLector(unsavedLectorInDepartment, department));
    }

    public int getCountOfLectors(String nameDepartment) {
        return 0;
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
}
