package com.epam.esm.mapper;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.epam.esm.mapper.ColumnName.*;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private TagMapper tagMapper;

    public GiftCertificateMapper() {
        tagMapper = new TagMapper();
    }

    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getInt(GIFT_ID));
        giftCertificate.setName(resultSet.getString(GIFT_NAME));
        giftCertificate.setDescription(resultSet.getString(GIFT_DESCRIPTION));
        giftCertificate.setPrice(resultSet.getDouble(GIFT_PRICE));
        giftCertificate.setDuration(resultSet.getInt(GIFT_DURATION));
        giftCertificate.setCreateDate((LocalDateTime) resultSet.getObject(GIFT_CREATE_DATE));
        giftCertificate.setLastUpdateDate((LocalDateTime) resultSet.getObject(GIFT_LAST_UPDATE_DATE));

        Tag tag = tagMapper.mapRow(resultSet, rowNum);
        giftCertificate.setTag(tag);

        return giftCertificate;
    }
}
