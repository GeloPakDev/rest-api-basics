package com.epam.esm.mapper;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.mapper.ColumnName.*;

public class GiftCertificateMapper implements ResultSetExtractor<List<GiftCertificate>> {

    @Override
    public List<GiftCertificate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        resultSet.next();
        while (!resultSet.isAfterLast()) {
            GiftCertificate giftCertificate = GiftCertificate.builder()
                    .id(resultSet.getLong(GIFT_ID))
                    .name(resultSet.getString(GIFT_NAME))
                    .description(resultSet.getString(GIFT_DESCRIPTION))
                    .price(resultSet.getDouble(GIFT_PRICE))
                    .duration(resultSet.getInt(GIFT_DURATION))
                    .createDate(resultSet.getTimestamp(GIFT_CREATE_DATE).toLocalDateTime())
                    .lastUpdateDate(resultSet.getTimestamp(GIFT_LAST_UPDATE_DATE).toLocalDateTime())
                    .build();

            List<Tag> tags = new ArrayList<>();

            while (!resultSet.isAfterLast() && resultSet.getInt(GIFT_ID) == giftCertificate.getId()) {
                Tag tag = Tag.builder()
                        .id(resultSet.getLong(TAG_ID))
                        .name(resultSet.getString(TAG_NAME))
                        .build();
                tags.add(tag);
                resultSet.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}