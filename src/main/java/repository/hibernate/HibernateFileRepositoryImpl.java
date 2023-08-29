package repository.hibernate;

import model.File;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.FileRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public File create(File file) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(file);
            transaction.commit();
            return file;
        }
    }

    @Override
    public File getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<File> query = session.createQuery(
                    "FROM File f WHERE f.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<File> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM File").list();
        }
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(file);
            transaction.commit();
            return file;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            File file = new File();
            file.setId(integer);
            session.delete(file);
            transaction.commit();
        }
    }
}
