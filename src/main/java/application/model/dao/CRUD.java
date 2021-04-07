package application.model.dao;

import java.util.List;

interface CRUD<T, ID> {
    void insert(T entity);

    void update(T entity);

    void deleteById(ID id);

    T findById(ID id);

    List<T> findAll();
}
