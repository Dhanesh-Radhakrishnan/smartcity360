package lk.jiat.smartcity360;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** Builds the SessionFactory once and shares it. Not a design-pattern class, just plumbing. */
public final class HibernateUtil {
    private static SessionFactory factory;

    private HibernateUtil() { }

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            // configure() reads hibernate.cfg.xml from resources
            factory = new Configuration().configure().buildSessionFactory();
            Runtime.getRuntime().addShutdownHook(new Thread(factory::close));
        }
        return factory;
    }
}