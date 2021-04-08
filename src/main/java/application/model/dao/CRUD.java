package application.model.dao;

import java.util.List;

interface CRUD<T, ID> {
    T insert(T entity);

    T update(T entity);

    void deleteById(ID id);

    T findById(ID id);

    List<T> findAll();
}
