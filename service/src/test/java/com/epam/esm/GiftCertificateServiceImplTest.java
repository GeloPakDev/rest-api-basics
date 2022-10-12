package com.epam.esm;

import com.epam.esm.impl.GiftCertificateDaoImpl;
import com.epam.esm.impl.GiftCertificateImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiftCertificateServiceImplTest {

    private GiftCertificateImpl giftCertificateService;
    private GiftCertificateDaoImpl giftCertificateDao;


    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        giftCertificateDao = new GiftCertificateDaoImpl(dataSource);
        giftCertificateService = new GiftCertificateImpl(giftCertificateDao);
    }

    @Test
    public void create() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 6L;
        Tag tag = new Tag(id, "tagName6");
        GiftCertificate giftCertificateToCreate = new GiftCertificate(id, "giftCertificate6", "description6", 6.66, 6, createDate, lastUpdateDate, tag);
        giftCertificateDao.create(giftCertificateToCreate);
        Optional<GiftCertificate> giftCertificateToFindOpt = giftCertificateDao.findById(6);
        GiftCertificate giftCertificateToFind = giftCertificateToFindOpt.get();

        boolean check = giftCertificateToCreate.equals(giftCertificateToFind);
        assertTrue(check);
    }

    @Test
    public void delete(){
        giftCertificateDao.delete(3);
        List<GiftCertificate> afterDelete = giftCertificateDao.findAll();
        assertEquals(4, afterDelete.size());
    }
}
