package com.epam.esm.impl;

import com.epam.esm.*;
import org.apache.commons.lang3.StringUtils;
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
    private GiftCertificateDao giftCertificateDao;

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

        Optional<GiftCertificate> certificate = giftCertificateDao.findById(id);

        GiftCertificate gift = certificate.get();

        if (giftCertificate.getName() != null && !StringUtils.isNumeric(giftCertificate.getName())) {
            gift.setName(giftCertificate.getName());
        }

        if (giftCertificate.getDescription() != null && !StringUtils.isNumeric(giftCertificate.getDescription())) {
            gift.setDescription(giftCertificate.getDescription());
        }

        if (giftCertificate.getPrice() != null) {
            gift.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificate.getDuration() != 0) {
            gift.setDuration(giftCertificate.getDuration());
        }

        gift.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateDao.update(gift);
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