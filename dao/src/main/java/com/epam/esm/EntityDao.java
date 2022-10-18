package com.epam.esm;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E, K> {
    Optional<E> findById(K id);

    List<E> findAll();

    void  create(E entity);

    boolean delete(K id);
}