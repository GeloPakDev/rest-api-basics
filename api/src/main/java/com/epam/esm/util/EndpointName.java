package com.epam.esm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointName {

    public static final String BASE_URL = "/api";
    public static final String TAGS = "/tags";
    public static final String GIFT_CERTIFICATES = "/certificates";
    public static final String FILTER = "/filter";
    public static final String ID = "/{id}";
}