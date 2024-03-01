package presentation;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.Recipe;
import repositories.RecipeRepo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class RecipePrincipal extends Application {
    private RecipeRepo recipeRepo = new RecipeRepo();
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des recettes");

        root = new BorderPane();

        // Création du menu
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Présentation");
        MenuItem textMenuItem = new MenuItem("Textuel");
        MenuItem graphMenuItem = new MenuItem("Graphique");

        // Action pour le menuItem textuelItem
        textMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                executeTextMenuItemAction();
            }
        });

        // Action pour le menuItem graphiqueItem
        graphMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                executeGraphMenuItemAction();
            }
        });

        menu.getItems().addAll(textMenuItem, graphMenuItem);
        menuBar.getMenus().add(menu);

        root.setTop(menuBar);

        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

    private void initRecipeRepo() {
        recipeRepo = new RecipeRepo();
        try {
            recipeRepo.init();
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }


    // Méthode exécutée lorsque le menuItem graphiqueItem est cliqué
    private void executeGraphMenuItemAction() {
        VBox buttonContainer = new VBox();
        List<String> functionNames = Arrays.asList(
                "Liste des recettes",
                "Nombre total d'oeufs",
                "Recettes utilisant de l'huile d'olive",
                "Nombre d'oeufs par recette",
                "Recette fournissant moins de 500 calories",
                "Quantité de sucre utilisée par la recette Zuppa Inglese",
                "2 premières étapes de la recette Zuppa Inglese",
                "Recettes avec plus de 5 étapes",
                "Recettes ne contenant pas de beurre",
                "Recettes ayant des ingrédients en commun avec la recette Zuppa Inglese",
                "Recette la plus calorique",
                "Unité la plus fréquente",
                "Nombre d'ingrédients par recette",
                "Recette la plus grasse",
                "Ingrédient le plus utilisé",
                "Recettes triées par nombre d'ingrédients",
                "Pour chaque ingrédient, les recettes qui l'utilisent",
                "Répartition des recettes par étape de réalisation",
                "Recette la plus simple"
        );

        for (String functionName : functionNames) {
            Button btn = new Button(functionName);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String result = executeFunctionAndGetResult(functionName); // Définissez cette méthode pour obtenir le résultat
                    displayResultInNewWindow(result);
                }
            });
            buttonContainer.getChildren().add(btn);
        }
        root.setCenter(buttonContainer);
    }

    private String executeFunctionAndGetResult(String functionName) {
        StringBuilder result = new StringBuilder();

        switch (functionName) {
            case "Liste des recettes":
                initRecipeRepo();
                result.append("Liste des recettes : \n");
                List<String> recipeTitles = recipeRepo.listRecipeTitles();
                recipeTitles.forEach(title -> result.append(title).append("\n"));
                break;
            case "Nombre total d'oeufs":
                initRecipeRepo();
                result.append("Nombre total d'oeufs : ").append(recipeRepo.countEggs()).append("\n");
                break;
            case "Recettes utilisant de l'huile d'olive":
                initRecipeRepo();
                result.append("Recettes utilisant de l'huile d'olive : \n");
                recipeRepo.listRecipesUsingOliveOil().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Nombre d'oeufs par recette":
                initRecipeRepo();
                result.append("Nombre d'oeufs par recette : \n");
                recipeRepo.countEggsPerRecipe().forEach((recipe, eggcount) -> {
                    result.append(recipe).append(" : ").append(eggcount).append(" oeuf(s)\n");
                });
                break;
            case "Recette fournissant moins de 500 calories":
                initRecipeRepo();
                result.append("Recette fournissant moins de 500 calories : \n");
                recipeRepo.listRecipesWithLessThan500Calories().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Quantité de sucre utilisée par la recette Zuppa Inglese":
                initRecipeRepo();
                result.append("Quantité de sucre utilisée par la recette Zuppa Inglese : ").append(recipeRepo.getZuppaIngleseSugarAmount()).append(" verre(s).\n");
                break;
            case "2 premières étapes de la recette Zuppa Inglese":
                initRecipeRepo();
                result.append("2 premières étapes de la recette Zuppa Inglese : \n");
                recipeRepo.getZuppaIngleseFirstTwoSteps().forEach(step -> result.append(step).append("\n"));
                break;
            case "Recettes avec plus de 5 étapes":
                initRecipeRepo();
                result.append("Recettes avec plus de 5 étapes : \n");
                recipeRepo.listRecipesWithMoreThanFiveSteps().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Recettes ne contenant pas de beurre":
                initRecipeRepo();
                result.append("Recettes ne contenant pas de beurre : \n");
                recipeRepo.listRecipesWithoutButter().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Recettes ayant des ingrédients en commun avec la recette Zuppa Inglese":
                initRecipeRepo();
                result.append("Recettes ayant des ingrédients en commun avec la recette Zuppa Inglese : \n");
                recipeRepo.listRecipesWithSharedIngredients().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Recette la plus calorique":
                initRecipeRepo();
                result.append("Recette la plus calorique : ").append(recipeRepo.printMostCaloricRecipe()).append("\n");
                break;
            case "Unité la plus fréquente":
                initRecipeRepo();
                result.append("Unité la plus fréquente : ").append(recipeRepo.getMostFrequentUnit()).append("\n");
                break;
            case "Nombre d'ingrédients par recette":
                initRecipeRepo();
                result.append("Nombre d'ingrédients par recette : \n");
                recipeRepo.numberOfIngredientPerRecipe().forEach((recipe, ingredientCount) -> {
                    result.append(recipe).append(" : ").append(ingredientCount).append(" ingrédient(s)\n");
                });
                break;
            case "Recette la plus grasse":
                initRecipeRepo();
                result.append("Recette la plus grasse : ").append(recipeRepo.fatestRecipe()).append("\n");
                break;
            case "Ingrédient le plus utilisé":
                initRecipeRepo();
                result.append("Ingrédient le plus utilisé : ").append(recipeRepo.getMostUsedIngredient()).append("\n");
                break;
            case "Recettes triées par nombre d'ingrédients":
                initRecipeRepo();
                result.append("Recettes triées par nombre d'ingrédients : \n");
                recipeRepo.sortByIngredientsNumber().forEach(recipe -> result.append(recipe).append("\n"));
                break;
            case "Pour chaque ingrédient, les recettes qui l'utilisent":
                initRecipeRepo();
                result.append("Pour chaque ingrédient, les recettes qui l'utilisent : \n");
                recipeRepo.displayRecipesByIngredient().forEach((ingredient, recipes) -> {
                    result.append(ingredient).append(" : ").append(recipes).append("\n");
                });
                break;
            case "Répartition des recettes par étape de réalisation":
                initRecipeRepo();
                result.append("Répartition des recettes par étape de réalisation : \n");
                recipeRepo.getStepsDistribution().forEach((step, recipeCount) -> {
                    result.append(step).append(" : ").append(recipeCount).append("\n");
                });
                break;
            case "Recette la plus simple":
                initRecipeRepo();
                result.append("Recette la plus simple : ").append(recipeRepo.getEasiestRecipe()).append("\n");
                break;

        }

        return result.toString();
    }

    private void displayResultInNewWindow(String result) {
        ResultWindow resultWindow = new ResultWindow(result);
        resultWindow.show();
    }

    private void executeTextMenuItemAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"", ButtonType.OK);
        alert.setTitle("Information");
        alert.setHeaderText("Affichage dans le terminal de commande");
        alert.showAndWait();

        initRecipeRepo();

        printExercice(4);
        System.out.println("Liste des recettes : ");
        recipeRepo.listRecipeTitles().forEach(System.out::println);

        printExercice(5);
        System.out.println("Nombre d'oeufs utilisés dans toutes les recettes : " + recipeRepo.countEggs());

        printExercice(6);
        System.out.println("Recettes utilisant de l'huile d'olive : ");
        recipeRepo.listRecipesUsingOliveOil().forEach(System.out::println);

        printExercice(7);
        System.out.println("Nombre d'oeufs par recette : ");
        recipeRepo.countEggsPerRecipe().forEach((recipe, eggcount) -> {
            System.out.println(recipe + " : " + eggcount + " oeuf(s)");
        });

        printExercice(8);
        System.out.println("Recettes fournissant moins de 500 calories : ");
        recipeRepo.listRecipesWithLessThan500Calories().forEach(System.out::println);

        printExercice(9);
        System.out.println("Quantité de sucre utilisée dans la recette Zuppa Inglese : " + recipeRepo.getZuppaIngleseSugarAmount() + " verre(s).");

        printExercice(10);
        System.out.println("2 premières étapes de la recette Zuppa Inglese : ");
        recipeRepo.getZuppaIngleseFirstTwoSteps().forEach(System.out::println);

        printExercice(11);
        System.out.println("Recettes avec plus de 5 étapes : ");
        recipeRepo.listRecipesWithMoreThanFiveSteps().forEach(System.out::println);

        printExercice(12);
        System.out.println("Recettes ne contenant pas de beurre : ");
        recipeRepo.listRecipesWithoutButter().forEach(System.out::println);

        printExercice(13);
        System.out.println("Recettes ayant des ingrédients en commun avec la recette Zuppa Inglese : ");
        recipeRepo.listRecipesWithSharedIngredients().forEach(System.out::println);

        printExercice(14);
        System.out.println("Recette la plus calorique : ");
        recipeRepo.printMostCaloricRecipe();

        printExercice(15);
        System.out.println("Unité la plus fréquente : " + recipeRepo.getMostFrequentUnit());

        printExercice(16);
        System.out.println("Nombre d'ingrédients par recette : ");
        recipeRepo.numberOfIngredientPerRecipe().forEach((recipe, ingredientCount) -> {
            System.out.println(recipe + " : " + ingredientCount + " ingrédient(s)");
        });

        printExercice(17);
        System.out.println("Recette la plus grasse : " + recipeRepo.fatestRecipe());

        printExercice(18);
        System.out.println("Ingrédient le plus utilisé : " + recipeRepo.getMostUsedIngredient());

        printExercice(19);
        System.out.println("Recettes triées par nombre d'ingrédients : ");
        recipeRepo.sortByIngredientsNumber().forEach(System.out::println);

        printExercice(20);
        System.out.println("Pour chaque ingrédient, les recettes qui l'utilisent : ");
        recipeRepo.displayRecipesByIngredient().forEach((ingredient, recipes) -> {
            System.out.println(ingredient + " : " + recipes);
        });

        printExercice(21);
        System.out.println("Répartition des recettes par étape de réalisation : ");
        recipeRepo.getStepsDistribution().forEach((step, recipeCount) -> {
            System.out.println(step + " : " + recipeCount);
        });

        printExercice(22);
        System.out.println("Recette la plus simple : " + recipeRepo.getEasiestRecipe());



    }

    private void printExercice(int noExercice){
        System.out.println("\n -- Exercice "+noExercice+" --\n");
    }
}