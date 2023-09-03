package repository.hibernate;

import model.EventEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.EventRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
    public EventEntity create(EventEntity eventEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(eventEntity);
            transaction.commit();
            return eventEntity;
        }
    }

    @Override
    public EventEntity getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<EventEntity> query = session.createQuery("FROM EventEntity e " +
                    "LEFT JOIN FETCH e.user " +
                    "LEFT JOIN FETCH e.file " +
                    "WHERE e.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<EventEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM EventEntity").list();
        }
    }

    @Override
    public EventEntity update(EventEntity eventEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(eventEntity);
            transaction.commit();
            return eventEntity;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            EventEntity eventEntity = new EventEntity();
            eventEntity.setId(integer);
            eventEntity.setUser(null);
            eventEntity.setFile(null);
            session.delete(eventEntity);
            transaction.commit();
        }
    }
}
