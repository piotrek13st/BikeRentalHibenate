/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        SessionFactory sf = null;
        try {
            
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            // Create the SessionFactory from hibernate.cfg.xml
            sf = configuration.buildSessionFactory(
			    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build() );
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sf;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}