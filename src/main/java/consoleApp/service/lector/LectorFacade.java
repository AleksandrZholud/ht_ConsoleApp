package consoleApp.service.lector;

import consoleApp.aspects.ConsoleColors;
import consoleApp.domain.enums.DEGREE;
import consoleApp.domain.model.Lector;
import consoleApp.UI.OutputMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorFacade {

    private final LectorService lectorService;

    public LectorFacade(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    public Lector findByFullName(String lectorsFullName) {
        return lectorService.findByFullName(lectorsFullName);
    }

    public void fillDbLectors(String fullNamesSeparatedByComa) {
        if (!Objects.equals(fullNamesSeparatedByComa, "")) {
            List<String> fullNames = new ArrayList<>(Arrays.asList(fullNamesSeparatedByComa.split(",")));
            fullNames.forEach(fullName -> {
                try {
                    String[] fullNameSplattedBySpace = fullName.split(" ");
                    Lector lector = getLectorFromTwoStrings(fullNameSplattedBySpace);
                    try {
                        lectorService.save(lector);
                        OutputMessage.showLoggedMessage(String.format(ConsoleColors.GREEN + "Lector: {inputted = %s} \"%s %s\" saved." + ConsoleColors.RESET,
                                fullName, lector.getName(), lector.getLastName()));
                    } catch (Exception e) {
                        OutputMessage.showLoggedMessage(String.format(ConsoleColors.YELLOW + "Lector %s exist in DB\n" + ConsoleColors.RESET,
                                lector.getFullName()));
                    }
                } catch (Exception e) {
                    OutputMessage.showLoggedMessage(String.format(ConsoleColors.RED + "Something wrong with FIO input: \"%s\"\n" + ConsoleColors.RESET,
                            fullName));
                }
            });
        }
    }

    public List<String> getAllFullNames() {
        return lectorService.getAllFullNames();
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

    private Lector getLectorFromTwoStrings(String[] fullNameSplattedBySpace) {
        String name;
        String lastName;
        int randomSalary = new Random().nextInt(10000);
        DEGREE degree = getRandomDegree();

        if (fullNameSplattedBySpace.length == 1) {
            return new Lector(fullNameSplattedBySpace[0], fullNameSplattedBySpace[0], degree, BigDecimal.valueOf(randomSalary));
        }

        if (fullNameSplattedBySpace[0].equals("") || fullNameSplattedBySpace[1].equals("")) {
            fullNameSplattedBySpace[0] = "0";
            fullNameSplattedBySpace[1] = "0";
        }
        name = fullNameSplattedBySpace[0];
        lastName = fullNameSplattedBySpace[1];

        return new Lector(name, lastName, degree, BigDecimal.valueOf(randomSalary));
    }
}
