package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class CommandeService implements IDao<Commande> {
    
    @Override
    public boolean create(Commande o) {
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
    public boolean update(Commande o) {
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
    public boolean delete(Commande o) {
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
    public Commande findById(int id) {
        Session session = HibernateUtil.getSession();
        try {
            return session.get(Commande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Commande> findAll() {
        Session session = HibernateUtil.getSession();
        try {
            Query<Commande> query = session.createQuery("FROM Commande", Commande.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver les commandes par date
    public List<Commande> findByDate(Date date) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Commande> query = session.createQuery(
                "FROM Commande c WHERE c.date = :date", Commande.class);
            query.setParameter("date", date);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver les commandes entre deux dates
    public List<Commande> findByDateRange(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Commande> query = session.createQuery(
                "FROM Commande c WHERE c.date BETWEEN :dateDebut AND :dateFin " +
                "ORDER BY c.date", Commande.class);
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
    
    // Méthode pour calculer le total d'une commande
    public double calculerTotalCommande(int commandeId) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Double> query = session.createQuery(
                "SELECT SUM(p.prix * lcp.quantite) " +
                "FROM Produit p JOIN p.ligneCommandes lcp JOIN lcp.commande c " +
                "WHERE c.id = :commandeId", Double.class);
            query.setParameter("commandeId", commandeId);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            session.close();
        }
    }
}
