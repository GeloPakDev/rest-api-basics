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
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<Tag> find(Integer id) {
        return tagDao.find(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public boolean create(Tag entity) {
        return tagDao.create(entity);
    }

    @Override
    public void delete(Integer id) {
        tagDao.delete(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagDao.findByName(name);
    }
}
