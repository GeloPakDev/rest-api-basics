package com.epam.esm.impl;

import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import com.epam.esm.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.QuerySQL.*;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_ID, new TagMapper(), id);
        return !list.isEmpty() ?
                Optional.of(list.get(0)) :
                Optional.empty();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new TagMapper());
    }

    @Override
    public void create(Tag entity) {
        jdbcTemplate.update(CREATE_TAG, entity.getName());
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(DELETE_TAG, id) != 0;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagMapper(), name);
        return !list.isEmpty() ?
                Optional.of(list.get(0)) :
                Optional.empty();
    }
}