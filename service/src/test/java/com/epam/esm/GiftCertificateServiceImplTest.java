package com.epam.esm;

import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.impl.GiftCertificateDaoImpl;
import com.epam.esm.impl.GiftCertificateImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }

    @Test
    public void create() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCreate = new GiftCertificate(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificateService.create(giftCertificateToCreate);
        Optional<GiftCertificate> giftCertificateToFindOpt = giftCertificateService.findById(5);
        GiftCertificate giftCertificateToFind = new GiftCertificate();
        if (giftCertificateToFindOpt.isPresent()) {
            giftCertificateToFind = giftCertificateToFindOpt.get();
        }
        System.out.println(giftCertificateToCreate);
        System.out.println(giftCertificateToFind);
        boolean check = giftCertificateToCreate.equals(giftCertificateToFind);
        assertTrue(check);
    }

    @Test
    public void delete() {
        giftCertificateService.delete(3);
        List<GiftCertificate> afterDelete = giftCertificateDao.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    public void update() throws IncorrectParameterException {
        int targetGift = 3;
        //Get certificate to update
        Optional<GiftCertificate> certificate = giftCertificateService.findById(targetGift);
        GiftCertificate giftCertificate = new GiftCertificate();
        if (certificate.isPresent()) {
            giftCertificate = certificate.get();
        }
        //Update it with new values
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificateService.update(3, giftCertificate);

        //Get updated GiftCertificate
        Optional<GiftCertificate> updatedCertificate = giftCertificateDao.findById(targetGift);
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }

        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }
}
