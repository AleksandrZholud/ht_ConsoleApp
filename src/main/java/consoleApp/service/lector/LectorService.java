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

    public Optional<Lector> findByFullName(String fullName) {
        return repository.findByFullName(fullName);
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

    public List<OutputMessage> fillDbLectors(String fullNamesSeparatedByComa) {
        List<OutputMessage> outputMessageList = new ArrayList<>();
        if (!fullNamesSeparatedByComa.isEmpty()) {
            List<String> fullNames = new ArrayList<>(Arrays.asList(fullNamesSeparatedByComa.split(",")));

            fullNames.stream().filter(Validate::validateInputByComa).filter(Validate::validateLectorNameInput).forEach(fullName -> {
                Lector lector = getNewLectorFromString(fullName);
                try {
                    this.save(lector);
                    outputMessageList.add(new OutputMessage(String.format(ConsoleColors.GREEN + "Lector: {inputted = %s} \"%s %s\" saved.",
                            fullName, lector.getName(), lector.getLastName())));
                } catch (Exception e) {
                    outputMessageList.add(new OutputMessage(String.format(ConsoleColors.YELLOW + "Lector %s exist in DB\n",
                            lector.getFullName())));
                }
            });
        } else {
            outputMessageList.add(new OutputMessage(ConsoleColors.YELLOW + "Empty input"));
        }
        return outputMessageList;
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