package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.EndpointName.*;
import static com.epam.esm.util.QueryParam.*;


@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins = LOCALHOST)
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    //GET mappings
    @RequestMapping(value = GIFT_CERTIFICATES, params = GIFT_ID)
    public ResponseEntity<GiftCertificate> findGiftById(@RequestParam int id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateService.find(id);
        return giftCertificate.map(
                        certificate -> new ResponseEntity<>(certificate, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @RequestMapping(GIFT_CERTIFICATES)
    public List<GiftCertificate> allGiftCertificates() {
        return giftCertificateService.findAll();
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = TAG_NAME)
    public List<GiftCertificate> findCertificatesByTagName(@RequestParam String tagName) {
        return giftCertificateService.findCertificatesByTagName(tagName);
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = PART_NAME)
    public List<GiftCertificate> findCertificatesByPartName(@RequestParam String partName) {
        return giftCertificateService.findCertificatesByPartName(partName);
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = PART_DESCRIPTION)
    public List<GiftCertificate> findCertificatesByPartDescription(@RequestParam String partDescription) {
        return giftCertificateService.findCertificatesByPartDescription(partDescription);
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_ASC)
    public List<GiftCertificate> sortCertificatesByDateASC() {
        return giftCertificateService.sortGiftByDateASC();
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_DESC)
    public List<GiftCertificate> sortCertificatesByDateDESC() {
        return giftCertificateService.sortGiftByDateDESC();
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_NAME_ASC)
    public List<GiftCertificate> sortCertificatesByNameASC() {
        return giftCertificateService.sortGiftByNameASC();
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_NAME_DESC)
    public List<GiftCertificate> sortCertificatesByNameDESC() {
        return giftCertificateService.sortGiftByNameDESC();
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_AND_NAME_DESC)
    public List<GiftCertificate> sortGiftByDateAndNameDESC() {
        return giftCertificateService.sortGiftByDateAndNameDESC();
    }

    //POST Mappings
    @PostMapping(value = GIFT_CERTIFICATES, consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.create(giftCertificate);
    }

    //UPDATE Mappings
    @PatchMapping(path = GIFT_CERTIFICATES + ID, consumes = JSON)
    public boolean updateGiftCertificate(@PathVariable(GIFT_ID) int id, @RequestBody GiftCertificate giftCertificate) {
        Optional<GiftCertificate> certificate = giftCertificateService.find(id);

        GiftCertificate gift = certificate.get();

        if (giftCertificate.getName() != null) {
            gift.setName(giftCertificate.getName());
        }

        if (giftCertificate.getDescription() != null) {
            gift.setDescription(giftCertificate.getDescription());
        }

        if (giftCertificate.getPrice() != null) {
            gift.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificate.getDuration() != 0) {
            gift.setDescription(giftCertificate.getDescription());
        }

        gift.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateService.update(gift);
    }

    //DELETE Mappings
    @DeleteMapping(GIFT_CERTIFICATES + ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable(GIFT_ID) int id) {
        giftCertificateService.delete(id);
    }
}