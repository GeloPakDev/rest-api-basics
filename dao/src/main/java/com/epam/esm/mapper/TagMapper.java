package com.epam.esm.mapper;

import com.epam.esm.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.mapper.ColumnName.*;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tag.builder().
                id(resultSet.getLong(TAG_ID))
                .name(resultSet.getString(TAG_NAME))
                .build();
    }
}