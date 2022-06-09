package consoleApp.service;

public class Validate {
    public static boolean validateLectorNameInput(String fullName) {
        try {
            String[] fullNameSplattedBySpace = fullName.split(" ");
            if (fullNameSplattedBySpace[0].equals("") || fullNameSplattedBySpace[1].equals("")) {
                return false;
            }
            return fullNameSplattedBySpace.length == 2;
        } catch (Exception e) {
            return false;
        }
    }
}
