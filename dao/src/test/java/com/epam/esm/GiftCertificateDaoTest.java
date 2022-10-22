package com.epam.esm;

import com.epam.esm.impl.GiftCertificateDaoImpl;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }


    @Test
    public void getGiftCertificateById() {
        Optional<GiftCertificate> giftCertificateOpt = giftCertificateDao.findById(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-10 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 5L;
        Tag tag = new Tag(id, "tagName5");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCompare = new GiftCertificate(2L, "bgiftCertificate2", "description2", 2.22, 2, createDate, lastUpdateDate, tags);
        GiftCertificate giftCertificate = new GiftCertificate();

        if (giftCertificateOpt.isPresent()) {
            giftCertificate = giftCertificateOpt.get();
        }

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
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }

        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }

    @Test
    public void deleteCertificate() {
        giftCertificateDao.delete(2);
        List<GiftCertificate> afterDelete = giftCertificateDao.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    public void createGiftCertificate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCreate = new GiftCertificate(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificateDao.create(giftCertificateToCreate);
        Optional<GiftCertificate> giftCertificateToFindOpt = giftCertificateDao.findById(5);
        GiftCertificate giftCertificateToFind = new GiftCertificate();
        if (giftCertificateToFindOpt.isPresent()) {
            giftCertificateToFind = giftCertificateToFindOpt.get();
        }
        boolean check = giftCertificateToCreate.equals(giftCertificateToFind);
        assertTrue(check);
    }

    @Test
    public void findCertificatesByNameDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "DESC");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    public void findCertificatesByNameASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "ASC");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals("agiftCertificate1", list.get(0).getName());
    }

    @Test
    public void findCertificatesByDateASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "ASC");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals("dgiftCertificate4", list.get(0).getName());
    }

    @Test
    public void findCertificatesByDateDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "DESC");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    public void findCertificatesByPartName() {
        Map<String, String> query = new HashMap<>();
        query.put("partName", "agiftCertificate1");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals(1, list.size());
    }

    @Test
    public void findCertificatesByDescName() {
        Map<String, String> query = new HashMap<>();
        query.put("partDescription", "description2");
        List<GiftCertificate> list = giftCertificateDao.getWithFilters(query);
        assertEquals(1, list.size());
    }
}