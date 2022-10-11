package com.epam.esm;


import java.util.Optional;

public interface TagDao extends EntityDao<Tag, Integer> {
    Optional<Tag> findByName(String name);
}