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

    public GiftCertificate validateForUpdate(GiftCertificate initialGift, GiftCertificate giftForUpdate) {
        String name = giftForUpdate.getName();
        if (name != null) {
            if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || !StringUtils.isNumeric(name)) {
                initialGift.setName(name);
            }
        }

        String description = giftForUpdate.getDescription();
        if (description != null) {
            if (description.length() > MAX_LENGTH_DESCRIPTION || !StringUtils.isNumeric(description)) {
                initialGift.setDescription(description);
            }
        }

        Double price = giftForUpdate.getPrice();
        if (price != null) {
            if (price > 0) {
                initialGift.setPrice(price);
            }
        }

        int duration = giftForUpdate.getDuration();
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            initialGift.setDuration(duration);
        }

        return initialGift;
    }
}