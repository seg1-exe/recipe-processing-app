package repositories;
import models.Recipe;
import models.Ingredient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeRepo {
    List<Recipe> recipies = new ArrayList<>();

    public void init() throws FileNotFoundException, XMLStreamException {
        try {
            File inputFile = new File("src/data/recipes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList recipeNodeList = doc.getElementsByTagName("rcp:recipe");
            for (int i = 0; i < recipeNodeList.getLength(); i++) {
                Element recipeNode = (Element) recipeNodeList.item(i);
                Recipe recipe = new Recipe();
                recipe.setId(recipeNode.getAttribute("id"));
                recipe.setTitle(recipeNode.getElementsByTagName("rcp:title").item(0).getTextContent());
                recipe.setDate(recipeNode.getElementsByTagName("rcp:date").item(0).getTextContent());
                NodeList ingredientNodeList = recipeNode.getElementsByTagName("rcp:ingredient");
                for (int j = 0; j < ingredientNodeList.getLength(); j++) {
                    Element ingredientNode = (Element) ingredientNodeList.item(j);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientNode.getAttribute("name"));
                    if(!ingredientNode.getAttribute("amount").isEmpty() && !ingredientNode.getAttribute("amount").equals("*")){
                        ingredient.setAmount(Double.parseDouble(ingredientNode.getAttribute("amount")));
                    }
                    if(!ingredientNode.getAttribute("unit").isEmpty()){
                        ingredient.setUnit(ingredientNode.getAttribute("unit"));
                    }

                    Element preparationNode = (Element) recipeNode.getElementsByTagName("rcp:preparation").item(0);
                    NodeList stepNodeList = preparationNode.getElementsByTagName("rcp:step");
                    for (int k = 0; k < stepNodeList.getLength(); k++) {
                        ingredient.addStep(stepNodeList.item(k).getTextContent());
                    }

                    recipe.addIngredient(ingredient);
                }

                if(recipeNode.getElementsByTagName("rcp:comment").item(0)!=null){
                    Element commentNode = (Element) recipeNode.getElementsByTagName("rcp:comment").item(0);
                    recipe.setComment(commentNode.getTextContent());
                }
                Element nutritionNode = (Element) recipeNode.getElementsByTagName("rcp:nutrition").item(0);
                recipe.setCalories(Integer.parseInt(nutritionNode.getAttribute("calories")));
                recipe.setFat(nutritionNode.getAttribute("fat"));
                recipe.setCarbohydrates(nutritionNode.getAttribute("carbohydrates"));
                recipe.setProtein(nutritionNode.getAttribute("protein"));
                recipies.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*for(Recipe recipe:recipies){
            System.out.println(recipe.getTitle());
        }*/
    }

    public static void printRecipies(List<Recipe> recipies){
        for(Recipe recipie:recipies){
            System.out.println("\t- "+ recipie.getTitle());
        }
    }


    /**
     * Méthode permettant de lister les titres des recettes
     * **/
    public void listRecipeTitles() {
        recipies.stream()
                .map(Recipe::getTitle)
                .forEach(System.out::println);
    }

    /**
     * Méthode permettant de calculer le nombre total d'oeufs utilisés dans les recettes
     */
    public int countEggs() {
        return recipies.stream()
                .flatMap(r ->r.getIngredients().stream())
                .filter(i -> i.getName().toLowerCase().contains("egg"))
                .mapToInt(i -> i.getAmount().intValue())
                .sum();
    }

    /**
     * Méthode permettant de lister les recettes utilisant de l'huile d'olive
     */
    public void listRecipesUsingOliveOil() {
        recipies.stream()
                .filter(r -> r.getIngredients().stream().anyMatch(i -> i.getName().toLowerCase().contains("olive oil")))
                .forEach(r -> System.out.println(r.getTitle()));
    }

    /**
     * Méthode permettant de calculer le nombre d'oeufs par recette
     * @return
     */
    public Map<String, Double> countEggsPerRecipe() {
        return recipies.stream()
                .collect(Collectors.toMap(
                        Recipe::toString,
                        recipe -> recipe.getIngredients().stream()
                                .filter(ingredient -> ingredient.getName().toLowerCase().contains("egg"))
                                .mapToDouble(Ingredient::getAmount)
                                .sum()
                ));
    }

    /**
     * Méthode listant les recettes fournissant moins de 500 calories
     */
    public void listRecipesWithLessThan500Calories() {
        recipies.stream()
                .filter(r -> r.getCalories() < 500)
                .forEach(r -> System.out.println(r.getTitle()));
    }

    /**
     * Méthode permettant de calculer la quantité de sucre utilisée dans la recette Zuppa Inglese
     * @return double
     */
    public double getZuppaIngleseSugarAmount() {
        return recipies.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .flatMap(r -> r.getIngredients().stream())
                .filter(i -> i.getName().toLowerCase().contains("sugar"))
                .mapToDouble(Ingredient::getAmount)
                .sum();
    }

    /**
     * Méthode permettant de lister les deux premières étapes de la recette Zuppa Inglese
     */
    public void printZuppaIngleseFirstTwoSteps() {
        Recipe zuppaInglese = recipies.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .findFirst()
                .orElse(null);

        if (zuppaInglese == null) {
            throw new RuntimeException("Zuppa Inglese recipe not found.");
        }

        zuppaInglese.getIngredients().stream()
                .findFirst()
                .ifPresent(ingredient -> {
                    System.out.println("Step 1: " + ingredient.getSteps().get(0));
                });

        zuppaInglese.getIngredients().stream()
                .findFirst()
                .ifPresent(ingredient -> {
                    System.out.println("Step 2: " + ingredient.getSteps().get(1));
                });
    }

    /**
     * Méthode permettant de lister les recettes avec plus de 5 étapes
     * @return List<Recipe>
     */
    public List<Recipe> listRecipesWithMoreThanFiveSteps() {
        return recipies.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .flatMap(ingredient -> ingredient.getSteps().stream())
                        .count() > 5)
                .collect(Collectors.toList());
    }

    /**
     * Méthode listant les recettes sans beurre
     * @return List<Recipe>
     */
    public List<Recipe> listRecipesWithoutButter() {
        return recipies.stream()
                .filter(r -> r.getIngredients().stream().noneMatch(i -> i.getName().toLowerCase().contains("butter")))
                .collect(Collectors.toList());
    }

    /**
     * Méthode listant les recettes avec des ingrédients partagés avec la recette Zuppa Inglese
     * @return List<Recipe>
     */
    public List<Recipe> listRecipesWithSharedIngredients() {
        Recipe zuppaInglese = recipies.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .findFirst()
                .orElse(null);

        if (zuppaInglese == null) {
            throw new RuntimeException("Zuppa Inglese recipe not found.");
        }

        return recipies.stream()
                .filter(r -> r.getIngredients().stream()
                        .anyMatch(i -> zuppaInglese.getIngredients().stream()
                                .anyMatch(zi -> zi.getName().equals(i.getName()))))
                .collect(Collectors.toList());
    }

    /**
     * Méthode affichant la recette la plus calorique
     */
    public void printMostCaloricRecipe() {
        Recipe mostCaloricRecipe = recipies.stream()
                .max((r1, r2) -> r1.getCalories() - r2.getCalories())
                .orElse(null);

        if (mostCaloricRecipe == null) {
            throw new RuntimeException("No recipe found.");
        }

        System.out.println(mostCaloricRecipe.getTitle());
    }

    /**
     * Méthode affichant l'unité de mesure la plus fréquente
     * @return String
     */
    public String getMostFrequentUnit() {
        return recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .filter(ingredient -> ingredient.getUnit() != null)
                .collect(Collectors.groupingBy(Ingredient::getUnit, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("");
    }

    /**
     * Méthode affichant le nombre d'ingrédients par recette
     */

    public void numberOfIngredientPerRecipe() {
        recipies.stream()
                .sorted(Comparator.comparingInt(r -> r.getIngredients().size()))
                .forEach(r -> System.out.println(r.getTitle() + " : " + r.getIngredients().size() + " ingredients"));
    }

    /**
     * Méthode affichant la recette avec le plus de matières grasses
     */
    public Recipe fatestRecipe() {
        return recipies.stream()
                .max(Comparator.comparing(recipe -> recipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getName().toLowerCase().contains("fat"))
                        .mapToDouble(Ingredient::getAmount)
                        .sum()))
                .orElse(null);
    }

    /**
     * Méthode affichant l'ingrédient le plus utilisé
     */
    public Ingredient getMostUsedIngredient() {
        Map<String, Long> ingredientCount = recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .collect(Collectors.groupingBy(Ingredient::getName, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostUsedEntry = ingredientCount.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        return mostUsedEntry.map(entry -> new Ingredient(entry.getKey(), (double) entry.getValue(), null))
                .orElse(null);
    }

    /**
     * Méthode triant les recettes par nombre d'ingrédients
     */
    public void sortByIngredientsNumber() {
        recipies.stream()
                .sorted(Comparator.comparingInt(r -> r.getIngredients().size()))
                .forEach(r -> System.out.println(r.getTitle() + " : " + r.getIngredients().size() + " ingredients"));
    }

    /**
     * Méthode affichant les recettes par ingrédient
     */
    public void displayRecipesByIngredient() {
        Map<String, List<Recipe>> recipesByIngredient = recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream().map(ingredient -> new AbstractMap.SimpleEntry<>(ingredient.getName(), recipe)))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())));

        recipesByIngredient.forEach((ingredient, recipes) -> {
            System.out.println("Ingredient: " + ingredient);
            recipes.forEach(recipe -> System.out.println("- " + recipe.getTitle()));
            System.out.println();
        });
    }

    /**
     * Méthode affichant la distribution des étapes
     * @return Map<String, Long>
     */
    public Map<String, Long> getStepsDistribution() {
        return recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .flatMap(ingredient -> ingredient.getSteps().stream())
                .collect(Collectors.groupingBy(step -> step, Collectors.counting()));
    }

    /**
     * Méthode retournant la recette la plus simple
     */
    public Optional<Recipe> getEasiestRecipe() {
        return recipies.stream()
                .min(Comparator.comparingInt(recipe -> recipe.getIngredients().stream()
                        .flatMap(ingredient -> ingredient.getSteps().stream())
                        .mapToInt(String::length)
                        .sum()));
    }


}
