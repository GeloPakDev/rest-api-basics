package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.EndpointName.*;
import static com.epam.esm.util.QueryParam.*;

@RestController
@RequestMapping(path = BASE_URL, produces = JSON)
@CrossOrigin(origins = LOCALHOST)
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

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
        }
        return giftCertificate;
    }

    @RequestMapping(GIFT_CERTIFICATES)
    public List<GiftCertificate> allGiftCertificates() {
        return giftCertificateService.findAll();
    }

    @RequestMapping(GIFT_CERTIFICATES + FILTER)
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) {
        return giftCertificateService.doFilter(allRequestParams);
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