package lk.jiat.smartcity360.chain;

import lk.jiat.smartcity360.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/** Talks to MySQL. The chain classes never see Hibernate. */
public class ComplaintRepository {

    public void save(Complaint complaint) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(complaint);   // INSERT complaint + trail rows
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public List<Complaint> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select c from Complaint c order by c.id desc", Complaint.class).list();
        }
    }
}