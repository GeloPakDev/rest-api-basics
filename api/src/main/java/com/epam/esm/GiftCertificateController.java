package com.epam.esm;

import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> findGiftById(@RequestParam int id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateService.findById(id);
        GiftCertificate giftCertificate;
        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, giftCertificate);
        } else {
            return ResponseHandler.generateResponse("Gift with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND, "[]");
        }
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
    public ResponseEntity<Object> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.create(giftCertificate);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED , HttpStatus.OK);
    }

    //UPDATE Mappings
    @PatchMapping(path = GIFT_CERTIFICATES + ID, consumes = JSON)
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable(GIFT_ID) int id, @RequestBody GiftCertificate giftCertificate) throws IncorrectParameterException {
        boolean check = giftCertificateService.update(id, giftCertificate);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_UPDATED + id, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.UPDATE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE Mappings
    @DeleteMapping(GIFT_CERTIFICATES + ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable(GIFT_ID) int id) {
        boolean check = giftCertificateService.delete(id);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED + id, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.DELETE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}