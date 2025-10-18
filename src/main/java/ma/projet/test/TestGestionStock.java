package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class TestGestionStock {
    
    public static void main(String[] args) {
        System.out.println("=== Test de l'application de gestion de stock ===\n");
        
        // Initialisation des services
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        
        // Test 1: Création des catégories
        System.out.println("1. Test de création des catégories:");
        testCreationCategories(categorieService);
        
        // Test 2: Création des produits
        System.out.println("\n2. Test de création des produits:");
        testCreationProduits(categorieService, produitService);
        
        // Test 3: Création des commandes
        System.out.println("\n3. Test de création des commandes:");
        testCreationCommandes(commandeService);
        
        // Test 4: Création des lignes de commande
        System.out.println("\n4. Test de création des lignes de commande:");
        testCreationLignesCommande(produitService, commandeService, ligneCommandeService);
        
        // Test 5: Affichage des produits par catégorie
        System.out.println("\n5. Test d'affichage des produits par catégorie:");
        testProduitsParCategorie(categorieService, produitService);
        
        // Test 6: Affichage des produits commandés entre deux dates
        System.out.println("\n6. Test d'affichage des produits commandés entre deux dates:");
        testProduitsCommandesEntreDates(produitService);
        
        // Test 7: Affichage des produits d'une commande donnée
        System.out.println("\n7. Test d'affichage des produits d'une commande donnée:");
        testProduitsCommande(produitService);
        
        // Test 8: Affichage des produits avec prix > 100 DH (requête nommée)
        System.out.println("\n8. Test d'affichage des produits avec prix > 100 DH:");
        testProduitsPrixSuperieur(produitService);
        
        System.out.println("\n=== Fin des tests ===");
        
        // Fermer proprement Hibernate
        ma.projet.util.HibernateUtil.shutdown();
    }
    
    private static void testCreationCategories(CategorieService categorieService) {
        Categorie categorie1 = new Categorie("ORD", "Ordinateurs");
        Categorie categorie2 = new Categorie("PER", "Périphériques");
        Categorie categorie3 = new Categorie("LOG", "Logiciels");
        
        boolean result1 = categorieService.create(categorie1);
        boolean result2 = categorieService.create(categorie2);
        boolean result3 = categorieService.create(categorie3);
        
        System.out.println("Catégorie 'Ordinateurs' créée: " + result1);
        System.out.println("Catégorie 'Périphériques' créée: " + result2);
        System.out.println("Catégorie 'Logiciels' créée: " + result3);
    }
    
    private static void testCreationProduits(CategorieService categorieService, ProduitService produitService) {
        // Récupérer les catégories créées
        Categorie ordinateurs = categorieService.findByCode("ORD");
        Categorie peripheriques = categorieService.findByCode("PER");
        Categorie logiciels = categorieService.findByCode("LOG");
        
        // Créer des produits
        Produit produit1 = new Produit("ES12", 120.0f, ordinateurs);
        Produit produit2 = new Produit("ZR85", 100.0f, peripheriques);
        Produit produit3 = new Produit("EE85", 200.0f, ordinateurs);
        Produit produit4 = new Produit("MS99", 150.0f, logiciels);
        Produit produit5 = new Produit("KB45", 80.0f, peripheriques);
        
        boolean result1 = produitService.create(produit1);
        boolean result2 = produitService.create(produit2);
        boolean result3 = produitService.create(produit3);
        boolean result4 = produitService.create(produit4);
        boolean result5 = produitService.create(produit5);
        
        System.out.println("Produit 'ES12' créé: " + result1);
        System.out.println("Produit 'ZR85' créé: " + result2);
        System.out.println("Produit 'EE85' créé: " + result3);
        System.out.println("Produit 'MS99' créé: " + result4);
        System.out.println("Produit 'KB45' créé: " + result5);
    }
    
    private static void testCreationCommandes(CommandeService commandeService) {
        Calendar cal = Calendar.getInstance();
        
        // Commande 1: 14 Mars 2013
        cal.set(2013, Calendar.MARCH, 14);
        Commande commande1 = new Commande(cal.getTime());
        
        // Commande 2: 15 Mars 2013
        cal.set(2013, Calendar.MARCH, 15);
        Commande commande2 = new Commande(cal.getTime());
        
        // Commande 3: 20 Mars 2013
        cal.set(2013, Calendar.MARCH, 20);
        Commande commande3 = new Commande(cal.getTime());
        
        boolean result1 = commandeService.create(commande1);
        boolean result2 = commandeService.create(commande2);
        boolean result3 = commandeService.create(commande3);
        
        System.out.println("Commande du 14 Mars 2013 créée: " + result1);
        System.out.println("Commande du 15 Mars 2013 créée: " + result2);
        System.out.println("Commande du 20 Mars 2013 créée: " + result3);
    }
    
    private static void testCreationLignesCommande(ProduitService produitService, CommandeService commandeService, LigneCommandeService ligneCommandeService) {
        // Récupérer les produits et commandes
        Produit es12 = produitService.findAll().stream().filter(p -> "ES12".equals(p.getReference())).findFirst().orElse(null);
        Produit zr85 = produitService.findAll().stream().filter(p -> "ZR85".equals(p.getReference())).findFirst().orElse(null);
        Produit ee85 = produitService.findAll().stream().filter(p -> "EE85".equals(p.getReference())).findFirst().orElse(null);
        
        Commande commande1 = commandeService.findAll().get(0); // Première commande
        
        // Créer les lignes de commande pour la première commande
        boolean result1 = ligneCommandeService.ajouterProduitCommande(es12, commande1, 7);
        boolean result2 = ligneCommandeService.ajouterProduitCommande(zr85, commande1, 14);
        boolean result3 = ligneCommandeService.ajouterProduitCommande(ee85, commande1, 5);
        
        System.out.println("Ligne commande ES12 (qty: 7) créée: " + result1);
        System.out.println("Ligne commande ZR85 (qty: 14) créée: " + result2);
        System.out.println("Ligne commande EE85 (qty: 5) créée: " + result3);
    }
    
    private static void testProduitsParCategorie(CategorieService categorieService, ProduitService produitService) {
        Categorie ordinateurs = categorieService.findByCode("ORD");
        if (ordinateurs != null) {
            List<Produit> produitsOrdinateurs = produitService.findByCategorie(ordinateurs);
            System.out.println("Produits de la catégorie 'Ordinateurs':");
            for (Produit p : produitsOrdinateurs) {
                System.out.println("- " + p.getReference() + " (" + p.getPrix() + " DH)");
            }
        }
    }
    
    private static void testProduitsCommandesEntreDates(ProduitService produitService) {
        Calendar cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 1);
        Date dateDebut = cal.getTime();
        
        cal.set(2013, Calendar.MARCH, 31);
        Date dateFin = cal.getTime();
        
        List<Object[]> resultats = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
        System.out.println("Produits commandés entre le 1er et 31 Mars 2013:");
        System.out.println("Référence   Prix    Quantité   Commande   Date");
        
        for (Object[] row : resultats) {
            String reference = (String) row[0];
            Float prix = (Float) row[1];
            Integer quantite = (Integer) row[2];
            Integer commandeId = (Integer) row[3];
            Date date = (Date) row[4];
            
            System.out.printf("%-10s %-8.0f DH %-9d %-9d %s%n", 
                reference, prix, quantite, commandeId, date);
        }
    }
    
    private static void testProduitsCommande(ProduitService produitService) {
        // Supposons que nous voulons afficher les produits de la commande avec ID 1
        produitService.afficherProduitsCommande(1);
    }
    
    private static void testProduitsPrixSuperieur(ProduitService produitService) {
        List<Produit> produits = produitService.findByPrixSuperieur(100.0f);
        System.out.println("Produits avec prix > 100 DH:");
        System.out.println("Référence   Prix");
        
        for (Produit p : produits) {
            System.out.printf("%-10s %.0f DH%n", p.getReference(), p.getPrix());
        }
    }
}
