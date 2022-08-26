import model.Ingredient;
import model.MeasureUnit;
import model.Recipe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/*
REcipe pool class have list of all recipes in a list
 */
public class RecipePool {
    private List<Recipe> recipes;

    public RecipePool() {
        loadDefaultRecipes();
    }

    public void addRecipe(Recipe recipe) {
        // validate redundant recipe before addition
        recipes.add(recipe);
    }

    public Recipe getRecipe(int recipeIndex) {
        return recipes.get(recipeIndex);
    }

    public boolean containsRecipe(String name) {
        return recipes.stream().anyMatch(recipe -> recipe.getName().equalsIgnoreCase(name));
    }

    public void updateRecipe(Recipe recipe) {
        boolean existingRecipe = false;
        int indexToUpdate = 0;
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getName().equals(recipe.getName())) {
                indexToUpdate = i;
                existingRecipe = true;
                break;
            }
        }
        if (existingRecipe) {
            recipes.remove(indexToUpdate);
            recipes.add(indexToUpdate, recipe);
        }
    }

    public List<Recipe> getRandomRecipesForOneWeek(Set<String> recipeToIgnore) {
        if (recipes.size() < 7) {
            return Collections.emptyList();
        }
        List<Recipe> recipesToShuffle = new ArrayList<>(recipes);
        Collections.shuffle(recipesToShuffle);
        final List<Recipe> randomRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipeToIgnore != null && !recipeToIgnore.contains(recipe.getName())) {
                randomRecipes.add(recipe);
            } else if (recipeToIgnore == null) {
                randomRecipes.add(recipe);
            }
            if (randomRecipes.size() == 7) {
                break;
            }
        }
        return randomRecipes;
    }

    /**
     * Add 10 default recipes while startup
     */
    private void loadDefaultRecipes() {
        recipes = new LinkedList<>();
        recipes.add(getRecipe("Sambar"));
        recipes.add(getRecipe("Soup"));
        recipes.add(getRecipe("Pizza"));
        recipes.add(getRecipe("Pasta"));
        recipes.add(getRecipe("Kebab"));
        recipes.add(getRecipe("Sushi"));
        recipes.add(getRecipe("kotbullar"));
        recipes.add(getRecipe("kotbullar1"));
        recipes.add(getRecipe("chicken wings"));
        recipes.add(getRecipe("lamb curry"));
    }

    private static Recipe getRecipe(String name) {
        Recipe recipe = new Recipe();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Lentils", MeasureUnit.Kilogram, 3));
        ingredients.add(new Ingredient("Tomato", MeasureUnit.Quantity, 2));
        recipe.setIngredients(ingredients);
        recipe.setName(name);
        List<String> steps = new ArrayList<>();
        steps.add("Boil lentils");
        steps.add("Cook tomato");
        recipe.setSteps(steps);
        return recipe;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}