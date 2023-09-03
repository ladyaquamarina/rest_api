package repository.hibernate;

import model.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.UserRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {
    @Override
    public UserEntity create(UserEntity userEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(userEntity);
            transaction.commit();
            return userEntity;
        }
    }

    @Override
    public UserEntity getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<UserEntity> query = session.createQuery(
                    "FROM UserEntity u LEFT JOIN FETCH u.events WHERE u.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<UserEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM UserEntity").list();
        }
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(userEntity);
            transaction.commit();
            return userEntity;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(integer);
            session.delete(userEntity);
            transaction.commit();
        }
    }
}
