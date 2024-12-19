<center>

# Projet de programmation fonctionnelle
**Gillier Arthur**
</center>

## Résumé

Ce projet a pour objet la manipulation et l’analyse d’un ensemble de recettes de cuisine au format XML, en s’appuyant sur une approche de programmation fonctionnelle en Java. En recourant aux flux (Streams) de l’API Java, le projet met en œuvre les opérations classiques de filtrage, de transformation et d’agrégation des données (pattern Map/Filter/Reduce). Les résultats sont accessibles via deux interfaces – textuelle (via le terminale de commande) et graphique (en utilisant JavaFX) – permettant d’explorer facilement les recettes, leurs ingrédients, leurs valeurs nutritionnelles, ainsi que divers attributs qualitatifs (commentaires, étapes, etc.).

Au-delà de la simple consultation, l’application permet des analyses plus avancées (calcul du nombre total d’œufs utilisés, identification des recettes les plus légères en calories, détermination de l’ingrédient le plus fréquent, etc.), démontrant ainsi la pertinence de la programmation fonctionnelle pour traiter efficacement des données semi-structurées.


## Introduction 

Dans un contexte où les données culinaires sont de plus en plus disponibles sous format numérique, l’objectif de ce projet est de développer une application Java centrée sur le paradigme fonctionnel, afin de traiter un corpus de recettes issues d’un fichier XML. Ce travail s’inscrit dans le cadre pédagogique de l’Université de La Rochelle, visant à consolider les compétences en programmation fonctionnelle, en manipulation d’API Stream, et en conception orientée-objet.

La principale originalité de cette application réside dans son utilisation intensive des opérations fonctionnelles disponibles en Java (map, filter, reduce, collect), dans le but d’extraire des connaissances pertinentes à partir d’un ensemble hétérogène de données culinaires.


## Contexte et Objectifs

Les objectifs pédagogiques et techniques sont multiples :

- Compréhension du paradigme fonctionnel en Java : Mise en pratique des lambdas, interfaces fonctionnelles et streams.
- Extraction et structuration des données : Lecture d’un fichier XML, parsing et initialisation d’une collection interne de recettes.
- Traitements avancés sur les données : Calcul de statistiques (nombre total d’œufs, quantités calorifiques), filtrages personnalisés (recettes à moins de 500 calories, sans beurre, etc.), et agrégations (ingrédient le plus utilisé, répartition par étapes).
- Double interface de présentation : Une interface textuelle pour un accès rapide aux résultats, et une interface graphique (JavaFX ou Swing) pour une visualisation plus intuitive.

## Architecture du système

Le code est organisé en plusieurs packages, reflétant une approche modulaire :

1. `models` : Contient les classes qui modélisent les entités du domaine, notamment :
    - **Recipe** : Représente une recette avec son titre, sa date, ses ingrédients, ses informations nutritionnelles, un commentaire et un lien vers d’autres recettes (related).
    - **Ingredient** : Représente un ingrédient, associé à un nom, une quantité, une unité et une liste éventuelle d’ingrédients composés. Cette classe stocke également les étapes de préparation qui s’y rattachent.
    - **Nutrition** : Encapsule les informations nutritionnelles d’une recette (protéines, glucides, lipides, calories).
    - **Related** : Permet d’établir des liens entre différentes recettes.

2. `repositories` : Contient la classe `RecipeRepo` chargée de la gestion des données. Elle assure notamment :
    - Le chargement du fichier XML (`recipes.xml`) et la création des objets Recipe.
    - La mise à disposition de méthodes d’accès et de traitement sémantiquement riches (listage des titres, calculs d’ingrédients, filtrage par contraintes diverses).

3. `presentation` : Classes liées à l’affichage et à l’interaction avec l’utilisateur.

    - **RecipePrincipal** : Point d’entrée de l’application, gestion du choix entre interface textuelle et graphique, chargement du référentiel, interaction avec l’utilisateur.
    - **ResultWindow** (exemple) : Présentation graphique des résultats.

Cette architecture sépare nettement la logique métier (extraction et traitement des données) de la présentation, favorisant la maintenabilité et l’extensibilité de l’application.

## Modélisation des entités

### Classe `Recipe`

Une `Recipe` est définie par un identifiant unique, un titre, une date, une liste d’ingrédients (`List<Ingredient>`), des informations nutritionnelles (`Nutrition`), un commentaire facultatif et une référence à d’autres recettes (via `Related`).

Extrait caractéristique :

- `List<Ingredient> ingredients` : Liste des ingrédients principaux de la recette.
- `Nutrition nutrition` : Informations protéiques, glucidiques, lipidiques et caloriques.
- Méthodes setter/getter pour manipuler ces attributs.

### Classe `Ingredient` 

`Ingredient` encapsule un nom, une quantité, une unité de mesure, une liste éventuelle d’ingrédients imbriqués (pour les compositions complexes), ainsi qu’une liste d’étapes de préparation.

Extrait caractéristique :

- `List<String> steps` : Les étapes de préparation associées à l’ingrédient, permettant d’associer non seulement une quantité, mais aussi un mode opératoire spécifique à cet ingrédient.

Ce niveau de granularité permet une modélisation fine des recettes, qui peuvent inclure des ingrédients composés ou plusieurs couches d’ingrédients.

### Classes `Nutrition` & `Related`

- `Nutrition` : Décrit la valeur nutritionnelle d’une recette (calories, protéines, etc.).
- `Related` : Lie une recette à une autre, permettant par exemple d’établir des suggestions de recettes complémentaires.

## Interface utilisateur

### Interface textuelle

L’interface textuelle permet une interaction simple, directement dans la console. L’utilisateur peut sélectionner une opération (par ex. afficher les titres, calculer le nombre total d’œufs) et observer le résultat immédiatement.

### Interface graphique

L’interface graphique offre une fenêtre principale avec des options de navigation, la possibilité d’afficher les résultats dans une fenêtre dédiée et, le cas échéant, de proposer une interaction plus conviviale (boutons, menus déroulants, etc.).

## Analyse et résultats

Grâce aux traitements implémentés, l’application met en lumière diverses informations utiles :

- Statistiques globales : Nombre total d’œufs, recette la plus calorique, répartition des recettes par étapes, etc.
- Filtrages sémantiques : Recettes sans beurre, recettes avec de l’huile d’olive, ou ne dépassant pas un certain seuil calorique.
- Points précis : Les deux premières étapes de la recette « Zuppa Inglese », l’unité de mesure la plus fréquente, l’ingrédient le plus utilisé, etc.

Ces résultats illustrent la flexibilité offerte par l’API Stream, permettant une manipulation déclarative des données, plus concise et plus claire que des boucles et conditions impératives traditionnelles.

## Difficultés rencontrées et limitations

- **Parsing XML** : Le traitement du fichier a nécessité une prise en main de l’API DOM. Bien que fonctionnelle, elle est parfois verbeuse. L’usage d’autres solutions (StAX, JAXB) pourrait simplifier le code.
- **Données incomplètes ou hétérogènes** : Certaines recettes peuvent omettre certaines unités, ou n’avoir que des quantités textuelles (« * »). Le code gère ces cas, mais au prix d’un certain nombre de vérifications.
- **Découverte de JavaFX** : L’interface graphique a été réalisée avec JavaFX, un framework graphique que je découvrais pour la première fois. Sans formation préalable, l’apprentissage s’est fait directement au cours du projet, rallongeant le temps de développement et l’effort de recherche. Cette expérience a toutefois permis d’acquérir une nouvelle compétence technique, mais a également augmenté la courbe d’apprentissage et la complexité des tâches d’implémentation.
- **Évolutivité** : L’ajout de nouveaux critères de filtrage ou de nouvelles méthodes analytiques est aisé grâce à l’approche fonctionnelle, mais l’évolutivité dépend également de la structure des données en entrée.

## Perspectives 

- **Amélioration du parsing** : Utiliser une bibliothèque plus haut niveau (JAXB) pour réduire la complexité du code d’initialisation.
- **Indexation et performances** : Sur un jeu de données plus volumineux, introduire des structures de données plus efficaces, ou un système de cache pour accélérer certains calculs récurrents.
- **Expérience utilisateur** : Améliorer l’interface graphique, offrir des filtres dynamiques, intégrer des graphiques (histogrammes, camemberts) pour visualiser la répartition des ingrédients, des unités ou des calories.

## Conclusion

Ce projet démontre l’efficacité du paradigme fonctionnel pour la manipulation de données semi-structurées en Java. L’usage des Streams, associé à une architecture modulaire clairement définie (distinction entre modèle, dépôt de données et présentation), permet une implémentation élégante et maintenable.

L’application produite peut aisément être étendue à d’autres types de données culinaires ou alimentaires, et sert d’exemple pratique pour la mise en œuvre des concepts de programmation fonctionnelle, de normalisation des données, et de manipulation des collections en Java.

## Remerciements

- Merci aux enseignants de l’Université de La Rochelle pour l’encadrement et le soutien dans la réalisation de ce projet.
- Inspiration pour la création du parser : [Article de Jérôme Frossard](https://www.epai-ict.ch/ict-modules/activities/m100-a7)
- Mise en place de JavaFX : [Tutoriel vidéo de EL AZHARI KHADIJA](https://www.youtube.com/watch?v=9Fy7f1K7Yec) et [Article Stack Overflow](https://stackoverflow.com/questions/53795661/javafx-modular-application-java-lang-module-findexception-module-javafx-contro)