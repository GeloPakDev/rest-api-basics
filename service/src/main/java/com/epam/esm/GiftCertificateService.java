package com.epam.esm;

import com.epam.esm.exception.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    Optional<GiftCertificate> findById(Integer id);

    List<GiftCertificate> findAll();

    void create(GiftCertificate giftCertificate);

    boolean delete(Integer id);

    boolean update(int id, GiftCertificate giftCertificate) throws IncorrectParameterException;

    boolean updateGiftTag(int id);

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams);
}