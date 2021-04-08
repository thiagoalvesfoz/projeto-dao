package application.model.dao;

import java.util.List;

interface CRUD<T, ID> {
    T save(T entity);

    T update(T entity);

    void deleteById(ID id);

    T findById(ID id);

    List<T> findAll();
}
