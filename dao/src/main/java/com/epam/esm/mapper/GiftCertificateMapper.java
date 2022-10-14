package com.epam.esm.mapper;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.mapper.ColumnName.*;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private TagMapper tagMapper;

    public GiftCertificateMapper() {
        tagMapper = new TagMapper();
    }

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tag tag = tagMapper.mapRow(resultSet, rowNum);
        return GiftCertificate.builder()
                .id(resultSet.getLong(GIFT_ID))
                .name(resultSet.getString(GIFT_NAME))
                .description(resultSet.getString(GIFT_DESCRIPTION))
                .price(resultSet.getDouble(GIFT_PRICE))
                .duration(resultSet.getInt(GIFT_DURATION))
                .createDate(resultSet.getTimestamp(GIFT_CREATE_DATE).toLocalDateTime())
                .lastUpdateDate(resultSet.getTimestamp(GIFT_LAST_UPDATE_DATE).toLocalDateTime())
                .tag(tag)
                .build();
    }
}