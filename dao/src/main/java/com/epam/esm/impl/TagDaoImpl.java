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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_ID, new TagMapper(), id);
        return list.size() == 0 ?
                Optional.empty() :
                Optional.of(list.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new TagMapper());
    }

    @Override
    public boolean create(Tag entity) {
        return jdbcTemplate.update(CREATE_TAG, entity.getName()) != 0;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagMapper(), name);
        return list.size() == 0 ?
                Optional.empty() :
                Optional.of(list.get(0));
    }
}