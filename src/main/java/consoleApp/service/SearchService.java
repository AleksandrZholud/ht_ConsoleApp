package consoleApp.service;

import consoleApp.UI.OutputMessage;
import consoleApp.service.department.DepartmentService;
import consoleApp.service.lector.LectorService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchService {

    private final LectorService lectorService;
    private final DepartmentService departmentService;

    public SearchService(LectorService lectorService, DepartmentService departmentService) {
        this.lectorService = lectorService;
        this.departmentService = departmentService;
    }

    public OutputMessage globalSearch(String template) {
        List<String> collectionOfAllLectorFullNamesAndDepartmentNames = lectorService.getAllFullNames().stream().sorted().collect(Collectors.toList());
        collectionOfAllLectorFullNamesAndDepartmentNames.addAll(departmentService.getAllNames().stream().sorted().collect(Collectors.toList()));
        collectionOfAllLectorFullNamesAndDepartmentNames = collectionOfAllLectorFullNamesAndDepartmentNames.stream()
                .filter(anyDepNameOrFio -> anyDepNameOrFio.toLowerCase().contains(template))
                .collect(Collectors.toList());
        return new OutputMessage(String.join(",", collectionOfAllLectorFullNamesAndDepartmentNames));
    }
}
