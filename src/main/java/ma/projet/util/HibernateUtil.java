package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    
    static {
        try {
            // Créer la SessionFactory à partir de hibernate.cfg.xml
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log l'exception, car elle pourrait être ignorée
            System.err.println("Erreur lors de l'initialisation de SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getSession() {
        return sessionFactory.openSession();
    }
    
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
    
    // Méthode pour fermer proprement toutes les ressources
    public static void shutdown() {
        closeSessionFactory();
    }
}
