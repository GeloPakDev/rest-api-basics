package com.epam.esm.impl;

import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import com.epam.esm.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        return tagDao.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public void create(Tag entity) {
         tagDao.create(entity);
    }

    @Override
    public boolean delete(Integer id) {
        return tagDao.delete(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }
}
