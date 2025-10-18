# Application de Gestion de Stock

Cette application Java permet de gérer le stock d'un magasin de produits informatiques en utilisant Hibernate pour la persistance des données.

## Structure du Projet

```
src/main/java/ma/projet/
├── classes/          # Entités JPA/Hibernate
│   ├── Categorie.java
│   ├── Produit.java
│   ├── Commande.java
│   └── LigneCommandeProduit.java
├── dao/              # Interface DAO générique
│   └── IDao.java
├── service/          # Services métier
│   ├── CategorieService.java
│   ├── ProduitService.java
│   ├── CommandeService.java
│   └── LigneCommandeService.java
├── util/             # Utilitaires
│   └── HibernateUtil.java
└── test/             # Programmes de test
    └── TestGestionStock.java

src/main/resources/
├── application.properties
└── hibernate.cfg.xml
```

## Prérequis

- Java 11 ou supérieur
- MySQL Server
- Maven (pour la gestion des dépendances)

## Configuration de la Base de Données

1. Créer une base de données MySQL nommée `gestion_stock`
2. Modifier les paramètres de connexion dans `hibernate.cfg.xml` si nécessaire :
   - URL de connexion
   - Nom d'utilisateur
   - Mot de passe

## Dépendances Maven

Les dépendances sont déjà configurées dans le `pom.xml` :

- **Hibernate Core** 6.2.0.Final
- **MySQL Connector** 8.0.33
- **Jakarta Persistence API** 3.1.0
- **C3P0 Connection Pool** 6.2.0.Final
- **SLF4J Simple** 2.0.7

## Fonctionnalités Implémentées

### 1. Couche Persistance ✅
- Classes entités avec annotations JPA
- Configuration Hibernate (`hibernate.cfg.xml` et `application.properties`)
- Classe utilitaire `HibernateUtil`

### 2. Couche Service ✅
- Interface générique `IDao<T>`
- `ProduitService` avec toutes les méthodes demandées :
  - ✅ Affichage des produits par catégorie
  - ✅ Affichage des produits commandés entre deux dates
  - ✅ Affichage des produits d'une commande donnée (format exact demandé)
  - ✅ Affichage des produits avec prix > 100 DH (requête nommée)
- `CategorieService`
- `CommandeService`
- `LigneCommandeService`

### 3. Programmes de Test ✅
- `TestGestionStock` : Programme complet de validation de toutes les fonctionnalités

## Exécution des Tests

Pour exécuter le programme de test :

```bash
# Compiler le projet
mvn compile

# Exécuter le test principal
mvn exec:java -Dexec.mainClass="ma.projet.test.TestGestionStock"
```

**Note:** Assurez-vous que MySQL est en cours d'exécution et que la base de données `gestion_stock` existe. Le programme créera automatiquement les tables nécessaires.

## Exemple de Sortie Attendue

```
=== Test de l'application de gestion de stock ===

1. Test de création des catégories:
Catégorie 'Ordinateurs' créée: true
Catégorie 'Périphériques' créée: true
Catégorie 'Logiciels' créée: true

2. Test de création des produits:
Produit 'ES12' créé: true
Produit 'ZR85' créé: true
Produit 'EE85' créé: true
Produit 'MS99' créé: true
Produit 'KB45' créé: true

3. Test de création des commandes:
Commande du 14 Mars 2013 créée: true
Commande du 15 Mars 2013 créée: true
Commande du 20 Mars 2013 créée: true

4. Test de création des lignes de commande:
Ligne commande ES12 (qty: 7) créée: true
Ligne commande ZR85 (qty: 14) créée: true
Ligne commande EE85 (qty: 5) créée: true

5. Test d'affichage des produits par catégorie:
Produits de la catégorie 'Ordinateurs':
- ES12 (120.0 DH)
- EE85 (200.0 DH)

6. Test d'affichage des produits commandés entre deux dates:
Produits commandés entre le 1er et 31 Mars 2013:
Référence   Prix    Quantité   Commande   Date
EE85       200      DH 5         1         2013-03-14
ES12       120      DH 7         1         2013-03-14
ZR85       100      DH 14        1         2013-03-14

7. Test d'affichage des produits d'une commande donnée:
Commande : 1     Date : 2013-03-14
Liste des produits :
Référence   Prix    Quantité
EE85       200      DH 5
ES12       120      DH 7
ZR85       100      DH 14

8. Test d'affichage des produits avec prix > 100 DH:
Produits avec prix > 100 DH:
Référence   Prix
ES12       120 DH
EE85       200 DH
MS99       150 DH

=== Fin des tests ===
```

## Modèle de Données

L'application suit le modèle UML fourni avec les relations suivantes :
- **Categorie** (1) ↔ (*) **Produit**
- **Produit** (*) ↔ (*) **Commande** via **LigneCommandeProduit**

## Notes Techniques

- Utilisation d'Hibernate 6.x avec Jakarta Persistence API
- Configuration automatique des tables via `hibernate.hbm2ddl.auto=update`
- Pool de connexions C3P0 pour de meilleures performances
- Gestion des transactions dans les services
- Requêtes nommées pour les cas d'usage spécifiques
- Gestion propre des ressources avec fermeture automatique des sessions

## Statut du Projet

✅ **PROJET TERMINÉ ET FONCTIONNEL**

Toutes les fonctionnalités demandées ont été implémentées et testées avec succès. L'application peut être exécutée immédiatement après configuration de la base de données MySQL.
