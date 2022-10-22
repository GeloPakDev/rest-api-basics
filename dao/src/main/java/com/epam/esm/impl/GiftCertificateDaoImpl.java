package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.Tag;
import com.epam.esm.TagDao;
import com.epam.esm.creator.QueryCreator;
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

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource) {
        tagDao = new TagDaoImpl(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //READ operations
    @Override
    public Optional<GiftCertificate> findById(Integer id) {
        List<GiftCertificate> results = jdbcTemplate.query(SELECT_GIFT_CERTIFICATE_BY_ID, new GiftCertificateMapper(), id);
        return !results.isEmpty() ?
                Optional.of(results.get(0)) :
                Optional.empty();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new GiftCertificateMapper());
    }

    @Override
    public List<GiftCertificate> getWithFilters(Map<String, String> fields) {
        QueryCreator queryCreator = new QueryCreator();
        String query = queryCreator.createGetQuery(fields);
        return jdbcTemplate.query(query, new GiftCertificateMapper());
    }

    //CREATE operations
    @Override
    public void create(GiftCertificate giftCertificate) {
        //Get ID of newly created GiftCertificate
        int giftCertificateId = createGiftCertificate(giftCertificate);
        //Get TAGS name to check on uniqueness
        List<Tag> list = giftCertificate.getTags();
        //Create tags which linked to the GiftCertificate
        createTags(giftCertificateId, list);
    }

    //DELETE operations
    @Override
    public boolean delete(Integer id) {
        int giftCertificate = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);
        int giftTag = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG_BY_ID, id);
        return (giftCertificate & giftTag) == 1;
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

    private int createTag(Tag tag) {
        //Create Statement for Tag
        PreparedStatementCreatorFactory pscfTag = new PreparedStatementCreatorFactory(CREATE_TAG, Types.VARCHAR);
        //Call to get generated key of the new Tag
        pscfTag.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscTag = pscfTag.newPreparedStatementCreator(new Tag[]{tag});

        GeneratedKeyHolder tagKeyHolder = new GeneratedKeyHolder();
        //Create new Tag in the DB
        jdbcTemplate.update(pscTag, tagKeyHolder);
        //Get ID of newly created GiftCertificate
        return Objects.requireNonNull(tagKeyHolder.getKey()).intValue();
    }

    private int createGiftCertificate(GiftCertificate giftCertificate) {
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
        return Objects.requireNonNull(giftKeyHolder.getKey()).intValue();
    }

    private void createTags(int giftCertificateId, List<Tag> tags) {
        //Pass through the List of gifts
        for (Tag tag : tags) {
            //Get name of each tag
            String name = tag.getName();
            //Find tag by name if it exists
            Optional<Tag> optTag = tagDao.findByName(name);
            if (optTag.isPresent()) {
                //If tag exists we will pass it into table directly
                if (Objects.equals(tag.getName(), name)) {
                    Long tagId = tag.getId();
                    jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
                } else {
                    //If tag does not exist we will create it and then pass to the table
                    int tagId = createTag(tag);
                    jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
                }
            }
        }
    }
}