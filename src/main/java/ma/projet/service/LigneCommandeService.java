package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    
    @Override
    public boolean create(LigneCommandeProduit o) {
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
    public boolean update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(int id) {
        Session session = HibernateUtil.getSession();
        try {
            return session.get(LigneCommandeProduit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<LigneCommandeProduit> findAll() {
        Session session = HibernateUtil.getSession();
        try {
            Query<LigneCommandeProduit> query = session.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver les lignes de commande par produit
    public List<LigneCommandeProduit> findByProduit(Produit produit) {
        Session session = HibernateUtil.getSession();
        try {
            Query<LigneCommandeProduit> query = session.createQuery(
                "FROM LigneCommandeProduit lcp WHERE lcp.produit = :produit", LigneCommandeProduit.class);
            query.setParameter("produit", produit);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver les lignes de commande par commande
    public List<LigneCommandeProduit> findByCommande(Commande commande) {
        Session session = HibernateUtil.getSession();
        try {
            Query<LigneCommandeProduit> query = session.createQuery(
                "FROM LigneCommandeProduit lcp WHERE lcp.commande = :commande", LigneCommandeProduit.class);
            query.setParameter("commande", commande);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver les lignes de commande par ID de commande
    public List<LigneCommandeProduit> findByCommandeId(int commandeId) {
        Session session = HibernateUtil.getSession();
        try {
            Query<LigneCommandeProduit> query = session.createQuery(
                "FROM LigneCommandeProduit lcp WHERE lcp.commande.id = :commandeId", LigneCommandeProduit.class);
            query.setParameter("commandeId", commandeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour calculer la quantité totale d'un produit commandé
    public int calculerQuantiteTotaleProduit(Produit produit) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Long> query = session.createQuery(
                "SELECT SUM(lcp.quantite) FROM LigneCommandeProduit lcp WHERE lcp.produit = :produit", Long.class);
            query.setParameter("produit", produit);
            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour créer une ligne de commande avec validation
    public boolean ajouterProduitCommande(Produit produit, Commande commande, int quantite) {
        if (quantite <= 0) {
            System.out.println("La quantité doit être positive");
            return false;
        }
        
        LigneCommandeProduit ligneCommande = new LigneCommandeProduit(quantite, produit, commande);
        return create(ligneCommande);
    }
}
