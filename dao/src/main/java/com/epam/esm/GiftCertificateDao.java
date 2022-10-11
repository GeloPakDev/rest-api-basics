package com.epam.esm;

import java.util.List;

public interface GiftCertificateDao extends EntityDao<GiftCertificate, Integer> {

    boolean update(GiftCertificate giftCertificate);

    boolean updateGiftTag(int id);

    List<GiftCertificate> findCertificatesByTagName(String name);

    List<GiftCertificate> findCertificatesByPartName(String name);

    List<GiftCertificate> findCertificatesByPartDescription(String name);

    List<GiftCertificate> sortGiftByNameDESC();

    List<GiftCertificate> sortGiftByNameASC();

    List<GiftCertificate> sortGiftByDateDESC();

    List<GiftCertificate> sortGiftByDateASC();

    List<GiftCertificate> sortGiftByDateAndNameDESC();
}