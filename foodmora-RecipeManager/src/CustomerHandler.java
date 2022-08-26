import model.Recipe;

import java.util.*;
import java.util.function.Function;
/*
Still Working on weeks recipes of customers--
 */
public class CustomerHandler implements FoodmoraUser {

    private static final List<String> OPTIONS = List.of("Generate Recipes", "View Recipes", "List My weeks", "Main Menu");
    private final Scanner scanner;

    private final RecipePool recipePool;

    private final Map<Integer, List<Recipe>> userRecipes = new HashMap<>();

    private final Map<String, Integer> mostUsedRecipes = new HashMap<>();

    public CustomerHandler(Scanner scanner, RecipePool recipePool) {
        this.scanner = scanner;
        this.recipePool = recipePool;
        loadDefaultPreviousWeekData();
    }

    public void initiateFlow(Function<Scanner, Boolean> mainMenu) {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        chooseOptions(mainMenu);
    }

    private void chooseOptions(Function<Scanner, Boolean> mainMenu) {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        System.out.println("The Customer");
        PrintHandler.printOptions(OPTIONS);
        PrintHandler.printChooseOption();
        try {
            if (scanner.hasNext()) {
                int option = Integer.parseInt(scanner.next());
                switch (option) {
                    case 1:
                        initiateGenerateRecipesFlow();
                        break;
                    case 2:
                        displayRecipes(getCurrentWeekNumber());
                        break;
                    case 3:
                        listUserWeeks();
                        break;
                    case 4:
                        callMainMenu(mainMenu, scanner);
                        return;
                    default:
                        PrintHandler.printOptions(OPTIONS);
                        PrintHandler.printChooseOption();
                }
            }
        } catch (Exception e) {
            chooseOptions(mainMenu);
        }
        chooseOptions(mainMenu);
    }

    private void listUserWeeks() {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        if (userRecipes.isEmpty()) {
            System.out.println("No weeks to display!!!");
            return;
        }
        Set<Integer> weeks = userRecipes.keySet();
        System.out.println("List of weeks");
        for (int weekNo : weeks) {
            System.out.println("[" + weekNo + "] - Week " + weekNo);
        }
        System.out.print("Enter Week number to view recipes :");
        int week = promptUserForIntegerAndValidate(scanner, weeks);
        System.out.println(userRecipes.get(week));
    }

    private void displayRecipes(int weekNo) {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        if (!userRecipes.containsKey(weekNo)) {
            System.out.println("No recipes to display");
            return;
        }
        System.out.println(userRecipes.get(weekNo));
    }

    private void initiateGenerateRecipesFlow() {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();

        int currentWeekNo = getCurrentWeekNumber();
        if (userRecipes.containsKey(currentWeekNo)) {
            System.out.printf("Do you want to regenerate for week %s (true or false) ?%n :", currentWeekNo);
            boolean flag = promptUserForBoolean(scanner);
            if (!flag) {
                return;
            }
        }
        System.out.printf("You are about generate recipe for week %s. Do you want to continue (True/False) ?%n :", currentWeekNo);
        boolean flag = promptUserForBoolean(scanner);
        if (flag) {
            List<Recipe> recipes = recipePool.getRandomRecipesForOneWeek(getMaxUsedRecipes());
            if (recipes.size() < 7) {
                System.out.println("Not enough recipes to generate for this week");
                return;
            }
            System.out.println(recipes);
            System.out.println("confirm selection (True/False)");
            flag = promptUserForBoolean(scanner);
            if (flag) {
                userRecipes.put(currentWeekNo, recipes);
                addWeightToRecipes(recipes);
            }
        }
    }

    private Set<String> getMaxUsedRecipes() {
        Integer max = mostUsedRecipes.values().stream().max(Comparator.naturalOrder()).orElse(0);
        Set<String> maxUsed = new HashSet<>();
        for (Map.Entry<String, Integer> recipe : mostUsedRecipes.entrySet()) {
            if (max.equals(recipe.getValue())) {
                maxUsed.add(recipe.getKey());
            }
        }
        return maxUsed;
    }

    private void addWeightToRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            String name = recipe.getName();
            if (mostUsedRecipes.containsKey(name)) {
                int weight = mostUsedRecipes.get(name) + 1;
                mostUsedRecipes.put(name, weight);
            } else {
                mostUsedRecipes.put(name, 1);
            }
        }
    }

    private int getCurrentWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
/*
Working on weeks recipes of customers
 */
    private void loadDefaultPreviousWeekData() {
        int currentWeekNumber = getCurrentWeekNumber();
        userRecipes.put(--currentWeekNumber, recipePool.getRandomRecipesForOneWeek(null));
        userRecipes.put(--currentWeekNumber, recipePool.getRandomRecipesForOneWeek(null));
        userRecipes.put(--currentWeekNumber, recipePool.getRandomRecipesForOneWeek(null));
        userRecipes.put(--currentWeekNumber, recipePool.getRandomRecipesForOneWeek(null));
    }
}
