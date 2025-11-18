## 🏠⚡ Home and Generator Management System (Interface Graphique - JavaFX)

Ce dépôt contient la version avec **interface utilisateur graphique (GUI)** du système de gestion de maisons et de générateurs, développé en **JavaFX**.

---

### 🌟 Aperçu du Projet

Le **Home and Generator Management System** modélise et simule des réseaux électriques simplifiés composés de générateurs et de maisons. L'objectif principal est de déterminer l'allocation de maisons aux générateurs la plus **efficace** en minimisant les coûts liés au **déséquilibre énergétique** et à la **surcharge (overload)**.

Cette version JavaFX transforme l'application console initiale en une solution interactive et visuelle, rendant l'analyse et la modification du réseau plus intuitive.

---

### ✨ Fonctionnalités Clés (GUI)

En plus de la logique métier existante, cette implémentation JavaFX permet :

* **Visualisation du Réseau :** Affichage clair et dynamique des maisons, des générateurs et de leurs connexions.
* **Gestion Interactive :** Ajouter, modifier ou supprimer des maisons et des générateurs directement via l'interface.
* **Modification des Connexions :** Drag-and-drop ou contrôles pour ajuster les allocations de maisons aux générateurs et observer l'impact en temps réel.
* **Affichage des Coûts :** Présentation graphique et numérique instantanée du coût total du réseau (déséquilibre et surcharge) pour évaluer l'efficacité de la configuration.
* **Comparaison de Configurations :** Outils pour charger, sauvegarder et comparer différentes solutions de réseau.


---

### 🔌 Logique Métier (Modèle de Réseau)

Le cœur de la simulation est basé sur les caractéristiques suivantes :

| Élément | Description | Consommation/Capacité |
| :--- | :--- | :--- |
| **Maisons** | Unités de consommation d'énergie. | *Faible* : 10 kW, *Normale* : 20 kW, *Forte* : 40 kW |
| **Générateurs** | Unités de production d'énergie. | **Capacité Maximale** configurable. |

#### Calcul des Coûts

Le coût total d'une configuration est calculée pour évaluer sa performance, en prenant en compte :

1.  **Déséquilibre de Charge :** Pénalité si la charge totale est loin de la capacité totale (efficacité).
2.  **Surcharge (Overload) :** Pénalité significative si la demande dépasse la capacité maximale d'un générateur.

---

### 🚀 Dépôt Initial (Logique Sans UI)

La logique métier et le modèle de données de base sont hérités et conservés dans le projet **Java console initial**.

* **Dépôt Original (sans UI) :** [https://github.com/BelharratMohamed/PAA_project](https://github.com/BelharratMohamed/PAA_project)

---
