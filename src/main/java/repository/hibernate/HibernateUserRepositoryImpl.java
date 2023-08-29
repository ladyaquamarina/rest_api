package repository.hibernate;

import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.UserRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public User create(User user) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    public User getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<User> query = session.createQuery(
                    "FROM User u LEFT JOIN FETCH u.events WHERE u.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setId(integer);
            session.delete(user);
            transaction.commit();
        }
    }
}
