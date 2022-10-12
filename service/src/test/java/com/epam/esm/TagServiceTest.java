package com.epam.esm;

import com.epam.esm.impl.TagDaoImpl;
import com.epam.esm.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceTest {

    private TagServiceImpl tagServiceImpl;
    private TagDaoImpl tagDaoImpl;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        tagDaoImpl = new TagDaoImpl(dataSource);
        tagServiceImpl = new TagServiceImpl(tagDaoImpl);
    }

    @Test
    public void findAll() {
        List<Tag> tags = tagServiceImpl.findAll();
        assertEquals(5, tags.size());
    }

    @Test
    public void findById() {
        Optional<Tag> tagOptional = tagServiceImpl.findById(2);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("red", tag.getName());
    }


    @Test
    public void create() {
        Tag tagToCreate = new Tag(6L, "Tag 6");
        tagServiceImpl.create(tagToCreate);
        Optional<Tag> tagOptional = tagServiceImpl.findById(6);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals(tag, tagToCreate);
    }

    @Test
    public void delete() {
        tagServiceImpl.delete(4);
        List<Tag> tags = tagServiceImpl.findAll();
        assertEquals(4, tags.size());
    }

    @Test
    public void findByName() {
        Optional<Tag> tagOptional = tagServiceImpl.findByName("red");
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("red", tag.getName());
    }
}