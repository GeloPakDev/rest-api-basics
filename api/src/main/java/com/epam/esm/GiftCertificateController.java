package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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
    public GiftCertificate findGiftById(@RequestParam int id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateService.findById(id);
        GiftCertificate giftCertificate = new GiftCertificate();
        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
        } else {
            throw new NoSuchElementException();
        }
        return giftCertificate;
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
        return giftCertificateService.update(id, giftCertificate);
    }

    //DELETE Mappings
    @DeleteMapping(GIFT_CERTIFICATES + ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable(GIFT_ID) int id) {
        giftCertificateService.delete(id);
    }
}