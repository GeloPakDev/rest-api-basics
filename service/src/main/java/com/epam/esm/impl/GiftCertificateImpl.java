package com.epam.esm.impl;

import com.epam.esm.*;
import com.epam.esm.validator.GiftValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.QueryParam.*;

@Service
public class GiftCertificateImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public Optional<GiftCertificate> findById(Integer id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    @Transactional
    public boolean create(GiftCertificate giftCertificate) {
        return giftCertificateDao.create(giftCertificate);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        giftCertificateDao.delete(id);
    }

    @Override
    public boolean update(int id, GiftCertificate giftCertificate) {
        //Get gift to update by ID
        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);
        //Extract Gift from Wrapper
        GiftCertificate gift = new GiftCertificate();
        if (certificate.isPresent()) {
            gift = certificate.get();
        }
        //Get Validated Gift
        GiftCertificate validatedGift = GiftValidator.validateForUpdate(gift);
        validatedGift.setLastUpdateDate(LocalDateTime.now());
        //Update GiftCertificate
        return giftCertificateDao.update(validatedGift);
    }

    @Override
    public boolean updateGiftTag(int id) {
        return giftCertificateDao.updateGiftTag(id);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) {
        Map<String, String> map = new HashMap<>();
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_NAME, getSingleRequestParameter(requestParams, PART_NAME));
        map.put(PART_DESCRIPTION, getSingleRequestParameter(requestParams, PART_DESCRIPTION));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_DATE, getSingleRequestParameter(requestParams, SORT_BY_DATE));
        return giftCertificateDao.getWithFilters(map);
    }

    private String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}