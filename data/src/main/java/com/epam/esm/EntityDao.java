package com.epam.esm;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E, K> {
    Optional<E> find(K id);

    List<E> findAll();

    boolean create(E entity);

    void delete(K id);
}