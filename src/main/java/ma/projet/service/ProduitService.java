package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    
    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSession();
        try {
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSession();
        try {
            Query<Produit> query = session.createQuery("FROM Produit", Produit.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour afficher la liste des produits par catégorie
    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Produit> query = session.createQuery(
                "FROM Produit p WHERE p.categorie = :categorie", Produit.class);
            query.setParameter("categorie", categorie);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour afficher les produits commandés entre deux dates
    public List<Object[]> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Object[]> query = session.createQuery(
                "SELECT p.reference, p.prix, lcp.quantite, c.id, c.date " +
                "FROM Produit p JOIN p.ligneCommandes lcp JOIN lcp.commande c " +
                "WHERE c.date BETWEEN :dateDebut AND :dateFin " +
                "ORDER BY c.id, p.reference", Object[].class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour afficher les produits commandés dans une commande donnée
    public List<Object[]> findProduitsByCommande(int commandeId) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Object[]> query = session.createQuery(
                "SELECT p.reference, p.prix, lcp.quantite " +
                "FROM Produit p JOIN p.ligneCommandes lcp JOIN lcp.commande c " +
                "WHERE c.id = :commandeId " +
                "ORDER BY p.reference", Object[].class);
            query.setParameter("commandeId", commandeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour afficher la liste des produits dont le prix est supérieur à 100 DH (requête nommée)
    public List<Produit> findByPrixSuperieur(float prix) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Produit> query = session.createNamedQuery("Produit.findByPrixSuperieur", Produit.class);
            query.setParameter("prix", prix);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode utilitaire pour afficher les produits d'une commande avec formatage
    public void afficherProduitsCommande(int commandeId) {
        Session session = HibernateUtil.getSession();
        try {
            // Récupérer les informations de la commande
            Commande commande = session.get(Commande.class, commandeId);
            if (commande == null) {
                System.out.println("Commande non trouvée avec l'ID: " + commandeId);
                return;
            }
            
            // Récupérer les produits de la commande
            List<Object[]> produits = findProduitsByCommande(commandeId);
            
            System.out.println("Commande : " + commande.getId() + "     Date : " + commande.getDate());
            System.out.println("Liste des produits :");
            System.out.println("Référence   Prix    Quantité");
            
            for (Object[] row : produits) {
                String reference = (String) row[0];
                Float prix = (Float) row[1];
                Integer quantite = (Integer) row[2];
                System.out.printf("%-10s %-8.0f DH %d%n", reference, prix, quantite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
