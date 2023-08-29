package repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    T create(T t);
    T getById(ID id);
    List<T> getAll();
    T update(T t);
    void deleteById(ID id);
}
