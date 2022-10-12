package com.epam.esm;

import com.epam.esm.impl.GiftCertificateDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateDaoTest {
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        giftCertificateDao = new GiftCertificateDaoImpl(dataSource);
    }

    @Test
    public void getGiftCertificateById() {
        Optional<GiftCertificate> giftCertificateOpt = giftCertificateDao.findById(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 2L;
        Tag tag = new Tag(id, "tagName2");
        GiftCertificate giftCertificateToCompare = new GiftCertificate(id, "giftCertificate2", "description2", 2.22, 2, createDate, lastUpdateDate, tag);
        GiftCertificate giftCertificate = new GiftCertificate();

        if (giftCertificateOpt.isPresent()) {
            giftCertificate = giftCertificateOpt.get();
        }
        System.out.println(giftCertificate);
        System.out.println(giftCertificateToCompare);

        boolean check = giftCertificate.equals(giftCertificateToCompare);
        assertTrue(check);
    }

    @Test
    public void getAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        assertEquals(5, giftCertificates.size());
    }

    @Test
    public void updateGiftCertificate() {
        int targetGift = 3;
        //Get certificate to update
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(targetGift);
        GiftCertificate giftCertificate = new GiftCertificate();
        if (certificate.isPresent()) {
            giftCertificate = certificate.get();
        }
        //Update it with new values
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificateDao.update(giftCertificate);

        //Get updated GiftCertificate
        Optional<GiftCertificate> updatedCertificate = giftCertificateDao.findById(targetGift);
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        if (certificate.isPresent()) {
            updatedGiftCertificate = certificate.get();
        }

        boolean check = updatedGiftCertificate.equals(giftCertificate);
        assertTrue(check);
    }

    @Test
    public void deleteCertificate() {


    }
}