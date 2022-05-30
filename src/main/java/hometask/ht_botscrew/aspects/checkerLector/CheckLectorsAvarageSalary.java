package hometask.ht_botscrew.aspects.checkerLector;


import hometask.ht_botscrew.aspects.CheckError;

public class CheckLectorsAvarageSalary implements CheckError {

    private String salary;

    public CheckLectorsAvarageSalary( String salary) {
        this.salary = salary;
    }

    @Override
    public boolean check() {
        return false;
    }
}
