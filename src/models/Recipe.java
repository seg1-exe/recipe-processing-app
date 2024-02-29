package models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    String id;
    String title;
    String date;

    List<Ingredient> ingredients;

    Nutrition nutrition;
    String comment;
    Related related;

    public Recipe() {
        this.nutrition = new Nutrition();
        this.ingredients = new ArrayList<>();
    }

    public Recipe(String id, String title, String date, Nutrition nutrition, String comment, Related related) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.nutrition = nutrition;
        this.comment = comment;
        this.related = related;
        ingredients = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }

    public void setCalories(int calories) {
        this.nutrition.setCalories(calories);
    }

    public void setFat(String fat) {
        this.nutrition.setFat(fat);
    }

    public void setCarbohydrates(String carbohydrates) {
        this.nutrition.setCarbohydrates(carbohydrates);
    }

    public void setProtein(String protein) {
        this.nutrition.setProtein(protein);
    }

    public int getCalories(){
        return this.nutrition.getCalories();
    }
}
