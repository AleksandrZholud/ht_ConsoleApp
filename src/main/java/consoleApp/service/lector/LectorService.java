package consoleApp.service.lector;

import consoleApp.UI.OutputMessage;
import consoleApp.aspects.ConsoleColors;
import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Lector;
import consoleApp.repository.LectorsRepository;
import consoleApp.service.AbstractMainService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorService extends AbstractMainService<Lector, Long, LectorsRepository> {

    public Lector findByFullName(String fio){
        return repository.findByFullName(fio).orElse(null);
    }

    public Optional<Lector> findById(Long id){
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

            fullNames.stream().filter(this::validateInput).forEach(fullName -> {
                Lector lector = getNewLectorFromString(fullName);
                try {
                    repository.save(getNewLectorFromString(fullName));
                    OutputMessage.showLoggedMessage(String.format(ConsoleColors.GREEN + "Lector: {inputted = %s} \"%s %s\" saved." + ConsoleColors.RESET,
                            fullName, lector.getName(), lector.getLastName()));
                } catch (Exception e) {
                    OutputMessage.showLoggedMessage(String.format(ConsoleColors.YELLOW + "Lector %s exist in DB\n" + ConsoleColors.RESET,
                            lector.getFullName()));
                }
            });
        }
    }

    private DEGREE getRandomDegree() {
        DEGREE degree;
        switch (new Random().nextInt(3)) {
            case 0:
                degree = DEGREE.ASSISTANT;
                break;
            case 1:
                degree = DEGREE.ASSOCIATE_PROFESSOR;
                break;
            default:
                degree = DEGREE.PROFESSOR;
                break;
        }
        return degree;
    }

    private Lector getNewLectorFromString(String fullName) {
        String[] fullNameSplattedBySpace = fullName.split(" ");
        String name = fullNameSplattedBySpace[0];
        String lastName = fullNameSplattedBySpace[1];
        DEGREE degree = getRandomDegree();
        int randomSalary = new Random().nextInt(10000);

        return new Lector(name, lastName, degree, BigDecimal.valueOf(randomSalary));
    }

    private boolean validateInput(String fullName) {
        String[] fullNameSplattedBySpace = fullName.split(" ");
        if (fullNameSplattedBySpace[0].equals("") || fullNameSplattedBySpace[1].equals("")) {
            return false;
        }
        return fullNameSplattedBySpace.length == 2;
    }
}