package model;

import java.util.ArrayList;
import java.util.List;
/*
Recipe class for Recipe name,List of ingredients, and list of steps
 */
public class Recipe {

    private String name;
    private List<Ingredient> ingredients;
    private List<String> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<String> getIngredientNames() {
        List<String> names = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            names.add(ingredient.getName());
        }
        return names;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "name=" + name + "\ningredients=" + ingredients + "\nsteps=" + steps +"\n";
    }
}
