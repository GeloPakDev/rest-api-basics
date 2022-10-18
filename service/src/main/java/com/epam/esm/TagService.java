package com.epam.esm;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TagService {

    Optional<Tag> findById(Integer id);

    List<Tag> findAll();

    void create(Tag entity);

    boolean delete(Integer id);

    Optional<Tag> findByName(String name);
}