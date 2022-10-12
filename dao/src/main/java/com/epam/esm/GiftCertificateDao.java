package com.epam.esm;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends EntityDao<GiftCertificate, Integer> {

    boolean update(GiftCertificate giftCertificate);

    boolean updateGiftTag(int id);

    List<GiftCertificate> getWithFilters(Map<String, String> fields);
}