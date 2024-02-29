package models;

public class Nutrition {
    String protein;
    String carbohydrates;
    String fat;
    int calories;

    public Nutrition() {
    }

    public Nutrition(String protein, String carbohydrates, String fat, int calories) {
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Nutrition [protein=" + protein + ", carbohydrates=" + carbohydrates + ", fat=" + fat + ", calories=" + calories + "]";
    }


    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
