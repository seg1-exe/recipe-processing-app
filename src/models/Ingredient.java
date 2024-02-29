package models;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private String name;
    private Double amount;
    private String unit;
    private List<Ingredient> ingredients;
    private List<String> steps;

    public Ingredient() {
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public Ingredient(String name, Double amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public Ingredient(String name, Double amount) {
        this.name = name;
        this.amount = amount;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.name;
    }


    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public void addStep(String step){
        this.steps.add(step);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
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
}
