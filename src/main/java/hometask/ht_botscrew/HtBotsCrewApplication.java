package hometask.ht_botscrew;

import hometask.ht_botscrew.domain.Department;
import hometask.ht_botscrew.domain.Lector;
import hometask.ht_botscrew.service.department.DepartmentService;
import hometask.ht_botscrew.service.lector.LectorFacade;
import hometask.ht_botscrew.service.lector.LectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class HtBotsCrewApplication implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final LectorFacade lectorFacade;

    public HtBotsCrewApplication(DepartmentService departmentService, LectorFacade lectorFacade) {
        this.departmentService = departmentService;
        this.lectorFacade = lectorFacade;
    }

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(HtBotsCrewApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    private static final Logger LOG = LoggerFactory
            .getLogger(HtBotsCrewApplication.class);

    Scanner in = new Scanner(System.in).useDelimiter("\n");

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");

        fillDB(in);

        System.out.print("\t\tAre u ready? ");
        while (in.nextInt() != 0) {

            LOG.info("Input Name of DEP for ADDING HEAD");
            String nameDepForADDHeadLector = in.next();
            Department department = departmentService.findByName(nameDepForADDHeadLector);

            LOG.info("Input FIO of Lector for ADDING HEAD");
            String lectorsFIO = in.next();
            Lector lectorForSave = lectorFacade.findByFIO(lectorsFIO).orElse(null);

            //////////////////////////////////////////////////////////////////////////////

            if(lectorForSave!=null) lectorForSave = lectorFacade.addDepartmentForLector(lectorForSave, department);
            LOG.info(String.format("   ======   lector %s in DEPARTMENT ADDED → → → ← ← ← OK   ======   \n", lectorForSave.getName()));
            lectorForSave = lectorFacade.findByFIO(lectorsFIO).orElse(null);

            department = departmentService.setHeadOfDepartment(lectorForSave, nameDepForADDHeadLector);
            if(department == null){
                LOG.error(String.format("   ======   lector is not contains in Department %s  ======   \n", nameDepForADDHeadLector));
                break;}
            else LOG.info(String.format("   ======   dep %s from ← ← ← DB GOT   ======   \n", department.getName()));

            int count = departmentService.getCountOfLectors(nameDepForADDHeadLector);
            LOG.info(String.format("\n\n========→========→========→========→========→========→%s\n", count));

            LOG.info("   ======   dep from ← ← ← DB GOT   ======   \n");
            LOG.info("   ======   lector saved into → → → DB   ======   \n");

            System.out.print("\t\tAre u ready ? ");
        }
    }

    private void fillDB(Scanner in) {
        LOG.info("   ======   Input string Names For DEPARTMENTS   ======  ");
        departmentService.fillDbDepartments(in.next());
        LOG.info("   ======   Input string Names For LECTORS   ======   ");
        lectorFacade.fillDbLectors(in.next());
    }
}