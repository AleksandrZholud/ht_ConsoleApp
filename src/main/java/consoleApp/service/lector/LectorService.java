package consoleApp.service.lector;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Lector;
import consoleApp.repository.LectorsRepository;
import consoleApp.service.AbstractMainService;
import consoleApp.service.Validate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorService extends AbstractMainService<Lector, LectorsRepository> {

    public Lector findByFullName(String fullName) {
        return repository.findByFullName(fullName).orElse(null);
    }

    public Optional<Lector> findById(Long id) {
        return repository.findById(id);
    }

    public List<Lector> findAllByDepartmentId(Long departmentId) {
        return repository.findAllByDepartmentId(departmentId);
    }

    public List<String> getAllFullNames() {
        return repository.getAllFullNames();
    }

    public void fillDbLectors(String fullNamesSeparatedByComa) {
        if (!fullNamesSeparatedByComa.isEmpty()) {
            List<String> fullNames = new ArrayList<>(Arrays.asList(fullNamesSeparatedByComa.split(",")));

            fullNames.stream().filter(Validate::validateLectorNameInput).forEach(fullName -> {
                Lector lector = getNewLectorFromString(fullName);
                try {
                    repository.save(lector);
                    OutputMessage.sout(String.format(ConsoleColors.GREEN + "Lector: {inputted = %s} \"%s %s\" saved." + ConsoleColors.RESET,
                            fullName, lector.getName(), lector.getLastName()));
                } catch (Exception e) {
                    OutputMessage.sout(String.format(ConsoleColors.YELLOW + "Lector %s exist in DB\n" + ConsoleColors.RESET,
                            lector.getFullName()));
                }
            });
        }
    }

    private DEGREE getRandomDegree() {
        switch (new Random().nextInt(3)) {
            case 0:
                return DEGREE.ASSISTANT;
            case 1:
                return DEGREE.ASSOCIATE_PROFESSOR;
            default:
                return DEGREE.PROFESSOR;
        }
    }

    private Lector getNewLectorFromString(String fullName) {
        String[] fullNameSplattedBySpace = fullName.split(" ");
        String name = fullNameSplattedBySpace[0];
        String lastName = fullNameSplattedBySpace[1];
        DEGREE degree = getRandomDegree();
        int randomSalary = new Random().nextInt(10000);

        return new Lector(name, lastName, degree, BigDecimal.valueOf(randomSalary));
    }
}