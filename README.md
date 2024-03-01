<center>

# Programmation Fonctionnelle
**Gillier Arthur**

## Gestion de recettes
</center>



## Description 

Ce projet est un projet de programmation fonctionnelle proposé par l'Université de La Rochelle. Son objectif est de mettre en pratique divers concepts clés de ce paradigme, tels que les fonctions lambda, les interfaces fonctionnelles, les streams, et les collections.

Le projet propose deux interfaces distinctes pour visualiser les données :

**Interface Textuelle**: Offre une représentation textuelle des données, facilitant leur analyse et leur compréhension.

**Interface Graphique**: Exploite des éléments visuels pour présenter les données de manière intuitive et interactive.
En implémentant ces deux interfaces, le projet vise à explorer les différentes options offertes par la programmation fonctionnelle pour la manipulation et la visualisation des données.


## Objectif

Développement d'une application Java pour la gestion et l'exploration d'un ensemble de recettes de cuisine stockées au format XML.

L'application s'appuie sur les principes de la programmation fonctionnelle et exploite l'API Stream de Java pour :

- Analyser et traiter les données contenues dans les fichiers XML.
- Filtrer et rechercher des recettes spécifiques en fonction de divers critères.

## Présentation

### Interface graphique :

![Fenêtre principale avec le choix de mode de présentation](/img/menuBase.png)

![Affichage d'une méthode](/img/interfaceGraphique.png)

### Interface textuelle :

![Pop-up d'information](/img/interfaceText.png)

![Affichage des méthodes dans le terminal](/img/terminal.png)

<br>

## Entités

## Package Models

### Recipe 

La classe Recipe représente une recette de cuisine. Elle contient les attributs suivants :
- id : Un identifiant unique de la recette.
- title : Un nom la représentant.
- date : La date de création de la recette.
- ingredients : Une liste d'ingrédients.
- nutrition : Les informations nutritionnelles de la recette.
- comment : Le ou les commentaire(s) de la recette.
- related : Donne les recettes allant bien ensemble

### Ingredient 

La classe Ingrédient encapsule les informations relatives à un ingrédient unique. Elle contient les attributs suivants :
- name: Le nom de l'ingrédient.
- amount: La quantité nécessaire de l'ingrédient, pouvant être un nombre entier ou décimal.
- unit: L'unité de mesure de la quantité.
- ingredients: Une liste imbriquée d'objets Ingredient pour les cas où un ingrédient est composé d'autres ingrédients.
- steps: Une liste d'instructions pour la préparation de l'ingrédient.

### Related 

La classe Related tisse des liens entre les recettes de notre application. Elle contient les attributs suivants :
- ref : Une référence à une autre recette.
- comment : Un texte facultatif offrant un commentaire.

### Nutrition 

La classe Nutrition joue un rôle clé dans la gestion des informations nutritionnelles au sein de l'application de recettes. Elle contient les attributs suivants :
- protein: Quantité de protéines contenue dans la recette.
- carbohydrates: Quantité de glucides présents dans la recette.
- fat: Quantité de lipides contenue dans la recette.
- calories: Nombre total de calories apportées par la recette.


## Package Repositories

### RecipeRepo 

La classe RecipeRepo joue un rôle central dans l'application de gestion de recettes. Elle est responsable du chargement des recettes depuis un fichier XML, de leur stockage en mémoire et de la mise à disposition de diverses méthodes pour manipuler et exploiter les données culinaires.

## Package Presentation

### RecipePrincipal 

La classe RecipePrincipal est le point d'entrée principal de l'application de gestion de recettes. Elle s'occupe de:

- L'initialisation de l'interface utilisateur: Crée la fenêtre principale, les menus et les boutons.
- Le chargement des données des recettes: Utilise la classe RecipeRepo pour charger les recettes depuis un fichier XML.
- L'affichage des résultats: Gère l'affichage des résultats des analyses et des calculs effectués sur les données des recettes.
- La gestion des interactions utilisateur: Détecte les clics sur les boutons et déclenche les actions correspondantes.

### ResultWindow

La classe ResultWindow sert à afficher les résultats des analyses effectuées sur les recettes au sein d'une fenêtre distincte. Elle prend en charge la création et la configuration de cette fenêtre.

## Auteur
- **seg1** - *Initial work* - [seg1](https://github.com/seg1-exe)

## Crédits

- Inspiration pour la création du parser : [Article de Jérôme Frossard](https://www.epai-ict.ch/ict-modules/activities/m100-a7)
- Mise en place de JavaFX : [Tutoriel vidéo de EL AZHARI KHADIJA](https://www.youtube.com/watch?v=9Fy7f1K7Yec) et [Article Stack Overflow](https://stackoverflow.com/questions/53795661/javafx-modular-application-java-lang-module-findexception-module-javafx-contro)






















