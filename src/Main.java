import models.Recipe;
import repositories.RecipeRepo;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        RecipeRepo recipeRepo = new RecipeRepo();
        try {
            recipeRepo.init();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        printExercice(4);
        System.out.println("Liste des recettes : ");
        recipeRepo.listRecipeTitles();

        printExercice(5);
        System.out.println("Nombre d'oeufs utilisés dans toutes les recettes : " + recipeRepo.countEggs());

        printExercice(6);
        System.out.println("Recettes utilisant de l'huile d'olive : ");
        recipeRepo.listRecipesUsingOliveOil();

        printExercice(7);
        System.out.println("Nombre d'oeufs par recette : ");
        recipeRepo.countEggsPerRecipe().forEach((recipe, eggcount) -> {
            System.out.println(recipe + " : " + eggcount + " oeuf(s)");
        });

        printExercice(8);
        System.out.println("Recettes fournissant moins de 500 calories : ");
        recipeRepo.listRecipesWithLessThan500Calories();

        printExercice(9);
        System.out.println("Quantité de sucre utilisée dans la recette Zuppa Inglese : " + recipeRepo.getZuppaIngleseSugarAmount() + " verre(s).");

        printExercice(10);
        System.out.println("Les deux premières étapes de la recette Zuppa Inglese : ");
        recipeRepo.printZuppaIngleseFirstTwoSteps();

        printExercice(11);
        System.out.println("Recettes avec plus de 5 étapes : ");
        recipeRepo.listRecipesWithMoreThanFiveSteps().forEach(r -> System.out.println(r.getTitle()));

        printExercice(12);
        System.out.println("Recettes sans beurre : ");
        recipeRepo.listRecipesWithoutButter().forEach(r -> System.out.println(r.getTitle()));

        printExercice(13);
        System.out.println("Recettes avec des ingrédients en commun avec Zuppa Inglese : ");
        recipeRepo.listRecipesWithSharedIngredients().forEach(r -> System.out.println(r.getTitle()));

        printExercice(14);
        System.out.println("Recette la plus calorique : ");
        recipeRepo.printMostCaloricRecipe();

        printExercice(15);
        System.out.println("Unité la plus utilisée : " + recipeRepo.getMostFrequentUnit());

        printExercice(16);
        System.out.println("Nombre d'ingrédients par recette : ");
        recipeRepo.numberOfIngredientPerRecipe();

        printExercice(17);
        System.out.println("Recette la plus grasse : " + recipeRepo.fatestRecipe());

        printExercice(18);
        System.out.println("Ingrédient le plus utilisé : "+recipeRepo.getMostUsedIngredient());

        printExercice(19);
        System.out.println("Recettes triées par nombre d'ingrédients : ");
        recipeRepo.sortByIngredientsNumber();

        printExercice(20);
        System.out.println("Recettes affichées par ingrédient : ");
        recipeRepo.displayRecipesByIngredient();

        printExercice(21);
        System.out.println("Répartition des recettes par étapes de préparation : ");
        recipeRepo.getStepsDistribution().entrySet().stream().forEach(entry -> System.out.println(entry.getKey()+ ": "+ entry.getValue()));

        printExercice(22);
        System.out.println("Recette la plus facile : " + recipeRepo.getEasiestRecipe().get());




    }

    public static void printExercice(int noExercice){
        System.out.println("\n********** Exercice "+noExercice+" **********\n");
    }
}