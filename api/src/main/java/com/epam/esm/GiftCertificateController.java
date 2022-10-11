package com.epam.esm;

import com.epam.esm.exceptions.EmptyResourceException;
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
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateService.find(id);
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
        List<GiftCertificate> list = giftCertificateService.findAll();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = TAG_NAME)
    public List<GiftCertificate> findCertificatesByTagName(@RequestParam String tagName) {
        List<GiftCertificate> list = giftCertificateService.findCertificatesByTagName(tagName);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = PART_NAME)
    public List<GiftCertificate> findCertificatesByPartName(@RequestParam String partName) {
        List<GiftCertificate> list = giftCertificateService.findCertificatesByPartName(partName);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = PART_DESCRIPTION)
    public List<GiftCertificate> findCertificatesByPartDescription(@RequestParam String partDescription) {
        List<GiftCertificate> list = giftCertificateService.findCertificatesByPartDescription(partDescription);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_ASC)
    public List<GiftCertificate> sortCertificatesByDateASC() {
        List<GiftCertificate> list = giftCertificateService.sortGiftByDateASC();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_DESC)
    public List<GiftCertificate> sortCertificatesByDateDESC() {
        List<GiftCertificate> list = giftCertificateService.sortGiftByDateDESC();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_NAME_ASC)
    public List<GiftCertificate> sortCertificatesByNameASC() {
        List<GiftCertificate> list = giftCertificateService.sortGiftByNameASC();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_NAME_DESC)
    public List<GiftCertificate> sortCertificatesByNameDESC() {
        List<GiftCertificate> list = giftCertificateService.sortGiftByNameDESC();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
    }

    @RequestMapping(value = GIFT_CERTIFICATES, params = SORT_BY_DATE_AND_NAME_DESC)
    public List<GiftCertificate> sortGiftByDateAndNameDESC() {
        List<GiftCertificate> list = giftCertificateService.sortGiftByDateAndNameDESC();
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new EmptyResourceException();
        }
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