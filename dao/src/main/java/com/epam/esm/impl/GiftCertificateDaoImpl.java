package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import com.epam.esm.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.QuerySQL.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private JdbcTemplate jdbcTemplate;
    private TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        tagDao = new TagDaoImpl(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //READ operations
    @Override
    public Optional<GiftCertificate> find(Integer id) {
        List<GiftCertificate> results = jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id);
        return results.isEmpty() ?
                Optional.of(results.get(0)) :
                Optional.empty();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> findCertificatesByTagName(String name) {
        return jdbcTemplate.query(SELECT_GIFT_BY_TAG_NAME, new GiftCertificateMapper(), name);
    }

    @Override
    public List<GiftCertificate> findCertificatesByPartName(String name) {
        return jdbcTemplate.query(SELECT_GIFT_BY_NAME_PART, new GiftCertificateMapper(), name);
    }

    @Override
    public List<GiftCertificate> findCertificatesByPartDescription(String description) {
        return jdbcTemplate.query(SELECT_GIFT_BY_DESCRIPTION_PART, new GiftCertificateMapper(), description);
    }

    @Override
    public List<GiftCertificate> sortGiftByNameDESC() {
        return jdbcTemplate.query(SORT_GIFT_BY_NAME_DESC, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> sortGiftByNameASC() {
        return jdbcTemplate.query(SORT_GIFT_BY_NAME_ASC, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> sortGiftByDateDESC() {
        return jdbcTemplate.query(SORT_GIFT_BY_DATE_DESC, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> sortGiftByDateASC() {
        return jdbcTemplate.query(SORT_GIFT_BY_DATE_ASC, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> sortGiftByDateAndNameDESC() {
        return jdbcTemplate.query(SORT_GIFT_BY_DATE_AND_NAME_DESC, new GiftCertificateMapper());
    }

    //CREATE operations
    @Override
    public boolean create(GiftCertificate giftCertificate) {
        //Create Statement for GiftCertificate
        PreparedStatementCreatorFactory pscfGift = new PreparedStatementCreatorFactory(
                CREATE_GIFT_CERTIFICATE,
                Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP
        );
        //Call to get generated key of the new GiftCertificate
        pscfGift.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscGift = pscfGift.newPreparedStatementCreator(
                Arrays.asList(
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getPrice(),
                        giftCertificate.getDuration(),
                        giftCertificate.getCreateDate(),
                        giftCertificate.getLastUpdateDate()));

        GeneratedKeyHolder giftKeyHolder = new GeneratedKeyHolder();
        //Create new Gift in DB
        jdbcTemplate.update(pscGift, giftKeyHolder);
        //Get ID of newly created GiftCertificate
        int giftCertificateId = Objects.requireNonNull(giftKeyHolder.getKey()).intValue();


        //Get TAG name to check on uniqueness
        String name = giftCertificate.getTag().getName();
        //Get Tag from DB
        Optional<Tag> tag = tagDao.findByName(name);
        //Check tags names on uniqueness
        if (tag.isPresent()) {
            //If they are identical we pass existing tag from DB
            if (Objects.equals(tag.get().getName(), name)) {
                Long tagId = tag.get().getId();
                return jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId) != 0;
            }
        }


        //Create Statement for Tag
        PreparedStatementCreatorFactory pscfTag = new PreparedStatementCreatorFactory(
                CREATE_TAG,
                Types.VARCHAR);
        //Call to get generated key of the new Tag
        pscfTag.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscTag = pscfTag.newPreparedStatementCreator(Collections.singletonList(giftCertificate.getTag().getName()));

        GeneratedKeyHolder tagKeyHolder = new GeneratedKeyHolder();
        //Create new Tag in the DB
        jdbcTemplate.update(pscTag, tagKeyHolder);
        //Get ID of newly created GiftCertificate
        int tagId = Objects.requireNonNull(tagKeyHolder.getKey()).intValue();
        //Insert ID of new entities in the DB
        return jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId) != 0;
    }

    //DELETE operations
    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG_BY_ID, id);
    }

    //UPDATE operations
    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                LocalDateTime.now(),
                giftCertificate.getId()) != 0;
    }

    @Override
    public boolean updateGiftTag(int id) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_TAG, id) != 0;
    }
}