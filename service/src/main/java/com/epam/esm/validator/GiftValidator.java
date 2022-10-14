package com.epam.esm.validator;

import com.epam.esm.GiftCertificate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class GiftValidator {
    private final int MAX_LENGTH_NAME = 45;
    private final int MIN_LENGTH_NAME = 3;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;

    public GiftCertificate validateForUpdate(GiftCertificate giftCertificate) {
        GiftCertificate gift = new GiftCertificate();

        String name = giftCertificate.getName();
        if (name != null) {
            if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || !StringUtils.isNumeric(name)) {
                gift.setName(name);
            }
        }

        String description = giftCertificate.getDescription();
        if (description != null) {
            if (description.length() > MAX_LENGTH_DESCRIPTION || !StringUtils.isNumeric(description)) {
                gift.setDescription(description);
            }
        }

        Double price = giftCertificate.getPrice();
        if (price != null) {
            if (price > 0) {
                gift.setPrice(giftCertificate.getPrice());
            }
        }

        int duration = giftCertificate.getDuration();
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            gift.setDuration(duration);
        }
        return gift;
    }
}