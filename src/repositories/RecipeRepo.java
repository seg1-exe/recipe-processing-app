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
    public List<String> listRecipeTitles() {
        return recipies.stream()
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
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
     * @return List<String>
     */
    public List<String> listRecipesUsingOliveOil() {
        return recipies.stream()
                .filter(r -> r.getIngredients().stream().anyMatch(i -> i.getName().toLowerCase().contains("olive oil")))
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
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

    public List<String> listRecipesWithLessThan500Calories() {
        return recipies.stream()
                .filter(r -> r.getCalories() < 500)
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
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
     * @return List<String>
     */
    public List<String> getZuppaIngleseFirstTwoSteps() {
        Recipe zuppaInglese = recipies.stream()
                .filter(r -> r.getTitle().equals("Zuppa Inglese"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Zuppa Inglese recipe not found."));

        List<String> firstTwoSteps = zuppaInglese.getIngredients().stream()
                .flatMap(ingredient -> ingredient.getSteps().stream())
                .limit(2)
                .collect(Collectors.toList());

        return firstTwoSteps;
    }

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
     * @return List<String>
     */
    public List<String> listRecipesWithMoreThanFiveSteps() {
        return recipies.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .flatMap(ingredient -> ingredient.getSteps().stream())
                        .count() > 5)
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Méthode permettant de lister les recettes sans beurre
     * @return List<String>
     */
    public List<String> listRecipesWithoutButter() {
        return recipies.stream()
                .filter(r -> r.getIngredients().stream().noneMatch(i -> i.getName().toLowerCase().contains("butter")))
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Méthode permettant de lister les recettes avec des ingrédients partagés avec la recette Zuppa Inglese
     * @return List<String>
     */
    public List<String> listRecipesWithSharedIngredients() {
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
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Méthode affichant la recette la plus calorique
     * @return String
     */
    public String printMostCaloricRecipe() {
        Recipe mostCaloricRecipe = recipies.stream()
                .max(Comparator.comparingInt(Recipe::getCalories))
                .orElse(null);

        if (mostCaloricRecipe == null) {
            throw new RuntimeException("No recipe found.");
        }

        return mostCaloricRecipe.getTitle();
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
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    /**
     * Méthode affichant le nombre d'ingrédients par recette
     * @return Map<String, Integer>
     */
    public Map<String, Integer> numberOfIngredientPerRecipe() {
        return recipies.stream()
                .collect(Collectors.toMap(Recipe::getTitle, recipe -> recipe.getIngredients().size()));
    }

    /**
     * Méthode affichant la recette avec le plus de matières grasses
     * @return String
     */
    public String fatestRecipe() {
        Recipe fatestRecipe = recipies.stream()
                .max(Comparator.comparingDouble(recipe -> recipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getName().toLowerCase().contains("fat"))
                        .mapToDouble(Ingredient::getAmount)
                        .sum()))
                .orElse(null);

        if (fatestRecipe == null) {
            throw new RuntimeException("No recipe found.");
        }

        return fatestRecipe.getTitle();
    }

    /**
     * Méthode affichant l'ingrédient le plus utilisé
     * @return String
     */
    public String getMostUsedIngredient() {
        Map<String, Long> ingredientCount = recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .collect(Collectors.groupingBy(Ingredient::getName, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostUsedEntry = ingredientCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return mostUsedEntry.map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Méthode triant les recettes par nombre d'ingrédients
     * @return List<String>
     */
    public List<String> sortByIngredientsNumber() {
        return recipies.stream()
                .sorted(Comparator.comparingInt(r -> r.getIngredients().size()))
                .map(Recipe::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Méthode affichant les recettes par ingrédient
     * @return Map<String, List<String>>
     */
    public Map<String, List<String>> displayRecipesByIngredient() {
        return recipies.stream()
                .flatMap(recipe -> recipe.getIngredients().stream()
                        .map(ingredient -> new AbstractMap.SimpleEntry<>(ingredient.getName(), recipe.getTitle())))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())));
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
     * @return Recipe
     */
    public Recipe getEasiestRecipe() {
        return recipies.stream()
                .min(Comparator.comparingInt(recipe -> recipe.getIngredients().stream()
                        .flatMap(ingredient -> ingredient.getSteps().stream())
                        .mapToInt(String::length)
                        .sum()))
                .orElse(null);
    }


}
