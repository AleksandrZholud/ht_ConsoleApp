package consoleApp.service.lector;

import consoleApp.aspects.CC;
import consoleApp.domain.DEGREE;
import consoleApp.domain.Lector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LectorFacade {

    private final LectorService lectorService;

    public LectorFacade(LectorService lectorService) {
        this.lectorService = lectorService;
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(LectorService.class);


    public Lector findByFullName(String lectorsFIO) {
        return lectorService.findByFullName(lectorsFIO).orElse(null);
    }

    public void fillDbLectors(String fiosSeparatedByComa) {
        if (!Objects.equals(fiosSeparatedByComa, "")) {
            List<String> fios = new ArrayList<>(Arrays.asList(fiosSeparatedByComa.split(",")));
            fios.forEach(lectorsName -> {
                try {
                    String[] names = lectorsName.split(" ");

                    String name;
                    String lastName;
                    int randomSalary;
                    DEGREE degree;

                    if (names.length == 1) {
                        if (names[0].equals("")) names[0] = "0";
                        name = names[0];
                        lastName = names[0];
                    } else {
                        if (names[0].equals("")) names[0] = "0";
                        if (names[1].equals("")) names[1] = "0";
                        name = names[0];
                        lastName = names[1];
                    }

                    switch (new Random().nextInt(3)) {
                        case 0:
                            degree = DEGREE.ASSISTANT;
                            randomSalary = new Random().nextInt(10000);
                            break;
                        case 1:
                            degree = DEGREE.ASSOCIATE_PROFESSOR;
                            randomSalary = new Random().nextInt(10000);
                            break;
                        default:
                            degree = DEGREE.PROFESSOR;
                            randomSalary = new Random().nextInt(10000);
                            break;
                    }

                    Lector tmp = new Lector(name, lastName, degree, BigDecimal.valueOf(randomSalary));
                    try {
                        lectorService.save(tmp);
                        System.out.println(String.format(CC.GREEN + "Lector: {inputted = %s} \"%s %s\" saved." + CC.RESET, lectorsName, tmp.getName(), tmp.getLastName()));
                    } catch (Exception e) {
                        System.out.printf(CC.YELLOW + "\t\t\t\t\t\t\t\t\tLector %s exist in DB\n" + CC.RESET, tmp.getFullName());
                    }
                } catch (Exception e) {
                    System.out.printf(CC.RED + "\t\t\t\t\t\t\t\t\tSomething wrong with FIO input: \"%s\"\n" + CC.RESET, lectorsName);
                }
            });
        }
    }

    public List<String> getAllFullNames() {
        return lectorService.getAllFullNames();
    }
}
