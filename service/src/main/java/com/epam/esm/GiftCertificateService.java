package com.epam.esm;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    Optional<GiftCertificate> findById(Integer id);

    List<GiftCertificate> findAll();

    boolean create(GiftCertificate giftCertificate);

    void delete(Integer id);

    boolean update(int id, GiftCertificate giftCertificate);

    boolean updateGiftTag(int id);

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams);
}