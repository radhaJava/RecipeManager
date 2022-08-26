import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
/*
Printhandler is to help other classes to  clearing screen and console and scan user input as integer
or String or print banner as title to look the application name and also print invalid option or
choose option to user  in console
 */
public class PrintHandler {
    public static void clearScreen() {
        String OperatingSystem = System.getProperty("os.name");

        if (OperatingSystem.toLowerCase().contains("windows")) {
            clearScreenWindows();
        } else {
            clearScreenUnix();
        }
    }

    private static void clearScreenUnix() {
        String clearScreenASCIICode = "\033[H\033[2J";

        System.out.print(clearScreenASCIICode);
        System.out.flush();
    }

    private static void clearScreenWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException error) {
            System.out.println("");
        }
    }

    public static Integer scanUserInput(String userInput) {

        int userInputNumber;
        if (userInput.trim().isEmpty()) {
            throw new InputMismatchException();
        } else {
            userInputNumber = Integer.parseInt(userInput);
            return userInputNumber;
        }
    }

    public static String scanUserInputString(String userInput) {

        String userInputString;
        if (userInput.trim().isEmpty()) {
            throw new InputMismatchException();
        } else {
            userInputString = userInput;
            return userInputString;
        }
    }

    public static void printBannerTitle() {
        System.out.println(" ====The Recipe Manager=== ");
    }

    public static void printOptions(List<String> options) {
        for (int index = 0; index < options.size(); index++) {
            int number = index + 1;
            String label = options.get(index);

            System.out.println("[" + number + "] " + label);
        }
    }

    public static boolean scanUserInputBoolean(String userBoolean) {
        if (!(userBoolean.trim().isEmpty())) {
            if (userBoolean.equalsIgnoreCase("True")) {
                return true;
            } else if (userBoolean.equalsIgnoreCase("False")) {
                return false;
            }
        }
        throw new InputMismatchException();
    }

    public static void printInvalidOption() {
        System.out.println("Invalid option! ");
    }

    public static void printInvalidInput() {
        System.out.print("Invalid input! Enter a valid input :");
    }

    public static void printChooseOption() {
        System.out.print("Please choose your option and press enter:");
    }
}