import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
/*
This is class which will have main option to print also end the application here by using scanner.
 */
public class FoodmoraManager {

    private static final List<String> USER_ROLES = List.of("Dietician", "Customer", "Exit");
    private final DieticianHandler dieticianHandler;
    private final CustomerHandler customerHandler;
    private final Scanner scanner;

    public FoodmoraManager() {
        RecipePool recipePool = new RecipePool();
        scanner = new Scanner(System.in);
        dieticianHandler = new DieticianHandler(scanner, recipePool);
        customerHandler = new CustomerHandler(scanner, recipePool);
    }

    public void startApplication() {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        chooseRole.apply(scanner);
    }

    private final Function<Scanner, Boolean> chooseRole = scannerInput -> {
        PrintHandler.printOptions(USER_ROLES);
        System.out.print("Please choose your role and press enter: ");
        try {
            int option = Integer.parseInt(scannerInput.nextLine());
            switch (option) {
                case 1:
                    initiateDieticianFlow();
                    break;
                case 2:
                    initiateCustomerFlow();
                    break;
                case 3:
                    break;
                default:
                    executeDefault();
            }
        } catch (Exception e) {
            executeDefault();
        }
        return true;
    };

    private void executeDefault() {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        PrintHandler.printInvalidOption();
        chooseRole.apply(scanner);
    }

    private void initiateDieticianFlow() {
        dieticianHandler.initiateFlow(chooseRole);
    }

    private void initiateCustomerFlow() {
        customerHandler.initiateFlow(chooseRole);
    }


    public void shutdownApplication() {
        scanner.close();
    }
}
