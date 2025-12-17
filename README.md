## Projet programmation avancée et applications - Réseau de distribution d'électricité

Ce dépôt contient la version avec **interface utilisateur graphique** du système de gestion de réseau de distribution d'électricité en utilisant **JavaFX**.

---

### Comment exécuter le projet

#### Prérequis

*   Java Development Kit (JDK) 21 ou supérieur.
*   Apache Maven.

#### Compilation et Lancement

Le projet utilise Maven pour la gestion des dépendances et la compilation.

1.  **Lancement simple :**

    Pour compiler le projet et lancer l'application, exécutez la commande suivante à la racine du projet :

    ```bash
    mvn clean javafx:run
    ```

2.  **Lancement avec un fichier de configuration :**

    Pour lancer l'application en chargeant directement un réseau depuis un fichier, vous pouvez passer le chemin du fichier en argument.

    ```bash
    mvn clean javafx:run -Dexec.args="<chemin_vers_le_fichier>"
    ```

    Par exemple :

    ```bash
    mvn clean javafx:run -Dexec.args="instance7.txt"
    ```

---

### Fonctionnalités Clés (GUI)

L'interface graphique est divisée en trois panneaux principaux pour une gestion intuitive :

*   **Vue Réseau (Panneau central) :**
    *   Visualisation dynamique des maisons, des générateurs et de leurs connexions.

*   **Panneau de Contrôle (Panneau de droite) :**
    *   **Gestion du Réseau :** Charger un réseau depuis un fichier ou en créer un nouveau.
    *   **Gestion des Composants :** Ajouter des maisons et des générateurs.
    *   **Gestion des Connexions :** Changer la connexion d'une maison.
    *   **Optimisation :** Lancer l'algorithme d'optimisation pour trouver la meilleure configuration et afficher le coût.

*   **Terminal (Panneau du bas) :**
    *   Affiche des journaux (logs) sur les actions effectuées (chargement, sauvegarde, erreurs, etc.).

---

### Logique Métier (Modèle de Réseau)

Le cœur de la simulation est basé sur les caractéristiques suivantes :

| Élément      | Description                       | Consommation/Capacité                          |
| :----------- | :-------------------------------- |:-----------------------------------------------|
| **Maisons**    | Unités de consommation d'énergie. | Faible : 10 kW, Normale : 20 kW, Forte : 40 kW |
| **Générateurs**| Unités de production d'énergie.   | Capacité configurable.                         |

#### Calcul des Coûts

Le coût total d'une configuration est calculé pour évaluer sa performance, en prenant en compte :

1.  **Déséquilibre de Charge :** Pénalité si la charge totale est loin de la capacité totale (efficacité).
2.  **Surcharge :** Pénalité significative si la demande dépasse la capacité maximale d'un générateur.
