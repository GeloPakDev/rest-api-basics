package com.epam.esm;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    Optional<GiftCertificate> findById(Integer id);

    List<GiftCertificate> findAll();

    List<GiftCertificate> findCertificatesByTagName(String name);

    List<GiftCertificate> findCertificatesByPartName(String name);

    List<GiftCertificate> findCertificatesByPartDescription(String description);

    List<GiftCertificate> sortGiftByNameDESC();

    List<GiftCertificate> sortGiftByNameASC();

    List<GiftCertificate> sortGiftByDateDESC();

    List<GiftCertificate> sortGiftByDateASC();

    List<GiftCertificate> sortGiftByDateAndNameDESC();

    boolean create(GiftCertificate giftCertificate);

    void delete(Integer id);

    boolean update(int id, GiftCertificate giftCertificate);

    boolean updateGiftTag(int id);
}