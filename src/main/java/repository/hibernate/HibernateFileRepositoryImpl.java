package repository.hibernate;

import model.FileEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.FileRepository;
import utils.HibernateUtils;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {
    @Override
    public FileEntity create(FileEntity fileEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(fileEntity);
            transaction.commit();
            return fileEntity;
        }
    }

    @Override
    public FileEntity getById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Query<FileEntity> query = session.createQuery(
                    "FROM FileEntity f WHERE f.id = :integer");
            query.setParameter("integer", integer);
            return query.list().size() > 0 ? query.list().get(0) : null;
        }
    }

    @Override
    public List<FileEntity> getAll() {
        try (Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM FileEntity").list();
        }
    }

    @Override
    public FileEntity update(FileEntity fileEntity) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(fileEntity);
            transaction.commit();
            return fileEntity;
        }
    }

    @Override
    public void deleteById(Integer integer) {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(integer);
            session.delete(fileEntity);
            transaction.commit();
        }
    }
}
