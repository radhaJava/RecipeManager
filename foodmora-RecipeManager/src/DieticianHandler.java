import model.Ingredient;
import model.MeasureUnit;
import model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
/*
* For user role Dietician to perform the actions of create , view and edit recipe using List.
 */
public class DieticianHandler implements FoodmoraUser {
    private static final List<String> OPTIONS = List.of("Create recipe", "View recipe pool", "Edit Recipe", "Main menu");
    private static final List<String> EDIT_RECIPE_OPTIONS = List.of("Add Ingredients", "Add steps", "Remove Ingredients", "Remove steps", "Main menu");
    private final Scanner scanner;
    private final RecipePool recipePool;

    public DieticianHandler(Scanner scanner, RecipePool recipePool) {
        this.scanner = scanner;
        this.recipePool = recipePool;
    }

    private void createRecipe() {
        System.out.println("Welcome To Create Recipe!");
        Recipe recipe = new Recipe();
        addRecipeName(recipe);
        if (recipePool.containsRecipe(recipe.getName())) {
            System.out.println("Recipe already exists");
            return;
        }
        addIngredients(recipe, true);
        addSteps(recipe);
        recipePool.addRecipe(recipe);
    }

    private void addRecipeName(Recipe recipe) {
        System.out.print("Enter recipe name: ");
        try {
            String recipeName = scanner.nextLine().trim();
            if (recipeName.isEmpty()) {
                throw new RuntimeException("Invalid recipeName");
            }
            recipe.setName(recipeName);
        } catch (Exception e) {
            PrintHandler.printInvalidOption();
            addRecipeName(recipe);
        }
    }

    private void addIngredients(Recipe recipe, boolean continueWithSteps) {

        List<Ingredient> ingredients = recipe.getIngredients() != null ? recipe.getIngredients() : new ArrayList<>();

        boolean addMoreIngredients;
        do {
            System.out.println("Add Ingredients");
            addIngredient(ingredients);
            System.out.print("Please enter true to add more ingredients or false to finish  :");
            addMoreIngredients = addMoreIngredients();

        } while(addMoreIngredients);
        recipe.setIngredients(ingredients);
    }

    private boolean addMoreIngredients() {
        try {
            return PrintHandler.scanUserInputBoolean(scanner.nextLine());
        } catch (Exception e) {
            PrintHandler.printInvalidInput();
            return addMoreIngredients();
        }
    }

    private void addIngredient(List<Ingredient> ingredients) {
        String name = addIngredientName();
        MeasureUnit measureUnit = addIngredientMeasure(name);
        Integer amount = addIngredientAmount(name, measureUnit);
        ingredients.add(new Ingredient(name, measureUnit, amount));
    }

    private String addIngredientName() {
        System.out.print("Please enter Ingredient name: ");
        try {
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new RuntimeException("Invalid input. ");
            }
            return name;
        } catch (Exception e) {
            return addIngredientName();
        }
    }

    private Integer addIngredientAmount(String name, MeasureUnit measureUnit) {
        System.out.printf("Please enter ingredient amount(%s) for recipe(%s)%n :", measureUnit.getUnit(), name);
        try {
            return Integer.parseInt(scanner.next().trim());
        } catch (Exception e) {
            return addIngredientAmount(name, measureUnit);
        }
    }

    private MeasureUnit addIngredientMeasure(String name) {
        PrintHandler.printOptions(MeasureUnit.UNIT_OF_MEASURE);
        System.out.print("Please choose measure unit for " + name +": ");
        try {
            int unit = Integer.parseInt(scanner.nextLine().trim());
            return MeasureUnit.fromIndex(unit);
        } catch (Exception e) {
            PrintHandler.printInvalidOption();
            return addIngredientMeasure(name);
        }
    }

    private void addSteps(Recipe recipe) {

        List<String> steps = recipe.getSteps() != null ? recipe.getSteps() : new ArrayList<>();
        boolean addMoreSteps;
        do {
            System.out.print("Add steps for recipe " + recipe.getName()+": ");
            steps.add(addStep(recipe.getName()));
            System.out.print("Please enter true to add more steps or false to finish :");
            addMoreSteps = addMoreSteps();
        } while (addMoreSteps);
        recipe.setSteps(steps);
    }

    private boolean addMoreSteps() {
        try {
            String input = scanner.nextLine().trim();
            return PrintHandler.scanUserInputBoolean(input);
        } catch (Exception e) {
            PrintHandler.printInvalidInput();
            return addMoreSteps();
        }
    }

    private String addStep(String name) {
        try {
            return PrintHandler.scanUserInputString(scanner.nextLine().trim());
        } catch (Exception e) {
            PrintHandler.clearScreen();
            return addStep(name);
        }
    }

    public void initiateFlow(Function<Scanner, Boolean> mainMenu) {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        chooseOptions(mainMenu);
    }

    private void chooseOptions(Function<Scanner, Boolean> mainMenu) {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        PrintHandler.printOptions(OPTIONS);
        PrintHandler.printChooseOption();
        try {
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    initiateCreateRecipeFlow();
                    break;
                case 2:
                    initiateViewRecipePool(mainMenu);
                    break;
                case 3:
                    initiateEditRecipeFlow(mainMenu);
                    break;
                case 4:
                    callMainMenu(mainMenu, scanner);
                    return;
                default:
                    chooseOptions(mainMenu);
                    break;
            }
        } catch (Exception e) {
            executeDefault(mainMenu);
        }
        chooseOptions(mainMenu);
    }

    private void initiateEditRecipeFlow(Function<Scanner, Boolean> mainMenu) {
        List<Recipe> recipes = recipePool.getRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes found");
            chooseOptions(mainMenu);
        }
        System.out.println("Recipes in Pool!!!");
        displayRecipes(recipePool.getRecipes());
        System.out.print("Please enter recipe number to edit :");
        Recipe recipeToEdit = findChosenRecipeFromPool();
        editChosenRecipe(recipeToEdit, mainMenu);
        recipePool.updateRecipe(recipeToEdit);
        System.out.println("Recipe updated!!!");

    }

    private Recipe findChosenRecipeFromPool() {
        try {
            int recipeIndex = PrintHandler.scanUserInput(scanner.nextLine());
            return recipePool.getRecipe(--recipeIndex);
        } catch (Exception e) {
            return findChosenRecipeFromPool();
        }
    }

    private void editChosenRecipe(Recipe recipeToEdit, Function<Scanner, Boolean> mainMenu) {
        System.out.println(recipeToEdit);
        PrintHandler.printOptions(EDIT_RECIPE_OPTIONS);
        PrintHandler.printChooseOption();
        try {
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    addIngredients(recipeToEdit, false);
                    return;
                case 2:
                    addSteps(recipeToEdit);
                    return;
                case 3:
                    removeIngredients(recipeToEdit);
                    return;
                case 4:
                    removeSteps(recipeToEdit);
                    return;
                case 5:
                    callMainMenu(mainMenu, scanner);
                    return;
                default:
                    PrintHandler.printOptions(OPTIONS);
                    PrintHandler.printChooseOption();
            }
        } catch (Exception e) {
            editChosenRecipe(recipeToEdit, mainMenu);
        }
    }

    private void removeSteps(Recipe recipeToEdit) {
        List<String> steps = recipeToEdit.getSteps();
        if (steps.size() < 2) {
            System.out.println("Not enough steps to remove..");
            return;
        }
        PrintHandler.printOptions(steps);
        PrintHandler.printChooseOption();
        int index = choseInputToRemove(steps);
        recipeToEdit.getSteps().remove(index - 1);
        recipePool.updateRecipe(recipeToEdit);
        System.out.println("Recipe updated!!!");
    }

    private void removeIngredients(Recipe recipeToEdit) {
        List<String> ingredientNames = recipeToEdit.getIngredientNames();
        if (ingredientNames.size() < 2) {
            System.out.println("Not enough ingredients to remove..");
            return;
        }
        PrintHandler.printOptions(ingredientNames);
        PrintHandler.printChooseOption();
        int index = choseInputToRemove(ingredientNames);
        recipeToEdit.getIngredients().remove(index - 1);
        recipePool.updateRecipe(recipeToEdit);
        System.out.println("Recipe updated!!!");
    }

    private int choseInputToRemove(List<String> input) {
        try {
            int index = PrintHandler.scanUserInput(scanner.nextLine().trim());
            input.get(index - 1);
            return index;
        } catch (Exception e) {
            return choseInputToRemove(input);
        }
    }

    private void executeDefault(Function<Scanner, Boolean> mainMenu) {
        chooseOptions(mainMenu);
    }

    private void initiateViewRecipePool(Function<Scanner, Boolean> mainMenu) {
        String recipes = recipePool.toString();
        if (recipes.isEmpty()) {
            System.out.println("No recipes found");
            chooseOptions(mainMenu);
        }
        System.out.println("Recipes in Pool!!!");
        displayRecipes(recipePool.getRecipes());


    }

    private void initiateCreateRecipeFlow() {
        PrintHandler.clearScreen();
        PrintHandler.printBannerTitle();
        System.out.println("Dietician!");
        createRecipe();
    }
}
