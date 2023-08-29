package repository.hibernate;

import model.Event;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.EventRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public Event create(Event event) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(event);
            transaction.commit();
            return event;
        }
    }

    @Override
    public Event getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Event> query = session.createQuery("FROM Event e " +
                    "LEFT JOIN FETCH e.user " +
                    "LEFT JOIN FETCH e.file " +
                    "WHERE e.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<Event> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM Event").list();
        }
    }

    @Override
    public Event update(Event event) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(event);
            transaction.commit();
            return event;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            Event event = new Event();
            event.setId(integer);
            event.setUser(null);
            event.setFile(null);
            session.delete(event);
            transaction.commit();
        }
    }
}
