package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CategorieService implements IDao<Categorie> {
    
    @Override
    public boolean create(Categorie o) {
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
    public boolean update(Categorie o) {
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
    public boolean delete(Categorie o) {
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
    public Categorie findById(int id) {
        Session session = HibernateUtil.getSession();
        try {
            return session.get(Categorie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Categorie> findAll() {
        Session session = HibernateUtil.getSession();
        try {
            Query<Categorie> query = session.createQuery("FROM Categorie", Categorie.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver une catégorie par son code
    public Categorie findByCode(String code) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Categorie> query = session.createQuery(
                "FROM Categorie c WHERE c.code = :code", Categorie.class);
            query.setParameter("code", code);
            List<Categorie> results = query.list();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    // Méthode pour trouver une catégorie par son libellé
    public Categorie findByLibelle(String libelle) {
        Session session = HibernateUtil.getSession();
        try {
            Query<Categorie> query = session.createQuery(
                "FROM Categorie c WHERE c.libelle = :libelle", Categorie.class);
            query.setParameter("libelle", libelle);
            List<Categorie> results = query.list();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
