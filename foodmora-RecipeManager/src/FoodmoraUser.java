import model.Recipe;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
/*
The interface  which have functions to implement in DieticianHandler and CustomerHAndler to perform
 */
public interface FoodmoraUser {

    default void callMainMenu(Function<Scanner, Boolean> mainMenu, Scanner scanner) {
        mainMenu.apply(scanner);
    }

    default void displayRecipes(List<Recipe> recipes) {
        if (recipes != null && !recipes.isEmpty()) {
            for (int i = 1; i <= recipes.size(); i++) {
                System.out.println("[" + i + "]" + recipes.get(i - 1));
            }
        }
    }

    default boolean promptUserForBoolean(Scanner scanner) {
        try {
            return PrintHandler.scanUserInputBoolean(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.print("Invalid input,Please enter a valid option:");
            return promptUserForBoolean(scanner);
        }
    }

    default Integer promptUserForIntegerAndValidate(Scanner scanner, Set<Integer> toValidateAgainst) {
        try {
            Integer input = PrintHandler.scanUserInput(scanner.nextLine().trim());
            if (!toValidateAgainst.contains(input)) {
                throw new RuntimeException("Invalid input");
            }
            return input;
        } catch (Exception e) {
            return promptUserForIntegerAndValidate(scanner, toValidateAgainst);
        }
    }
}
