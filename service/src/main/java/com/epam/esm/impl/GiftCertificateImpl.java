package com.epam.esm.impl;

import com.epam.esm.GiftCertificate;
import com.epam.esm.GiftCertificateDao;
import com.epam.esm.GiftCertificateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public Optional<GiftCertificate> find(Integer id) {
        return giftCertificateDao.find(id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public List<GiftCertificate> findCertificatesByTagName(String name) {
        return giftCertificateDao.findCertificatesByTagName(name);
    }

    @Override
    public List<GiftCertificate> findCertificatesByPartName(String name) {
        return giftCertificateDao.findCertificatesByPartName(name);
    }

    @Override
    public List<GiftCertificate> findCertificatesByPartDescription(String description) {
        return giftCertificateDao.findCertificatesByPartDescription(description);
    }

    @Override
    public List<GiftCertificate> sortGiftByNameDESC() {
        return giftCertificateDao.sortGiftByNameDESC();
    }

    @Override
    public List<GiftCertificate> sortGiftByNameASC() {
        return giftCertificateDao.sortGiftByNameASC();
    }

    @Override
    public List<GiftCertificate> sortGiftByDateDESC() {
        return giftCertificateDao.sortGiftByDateDESC();
    }

    @Override
    public List<GiftCertificate> sortGiftByDateASC() {
        return giftCertificateDao.sortGiftByDateASC();
    }

    @Override
    public List<GiftCertificate> sortGiftByDateAndNameDESC() {
        return giftCertificateDao.sortGiftByDateAndNameDESC();
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

        Optional<GiftCertificate> certificate = giftCertificateDao.find(id);

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
}