package com.epam.esm.creator;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueryCreator {
    public static final String BASE_QUERY = """
            SELECT gc.id,
                   gc.name,
                   gc.description,
                   gc.price,
                   gc.duration,
                   gc.create_date,
                   gc.last_update_date,
                   t.tag_id,
                   t.tag_name
            FROM gift_tags gt
            JOIN gift_certificate gc ON gt.gift_id = gc.id
            JOIN tag t ON gt.tag_id = t.tag_id
            """;

    public static final String TAG_NAME = "WHERE t.tag_name = ";
    public static final String NAME_PART = "WHERE gc.name LIKE ";
    public static final String DESCRIPTION_PART = "WHERE gc.description LIKE ";
    public static final String ORDER_BY_NAME = "ORDER BY gc.name ";
    public static final String ORDER_BY_DATE = "ORDER BY gc.create_date ";

    public static String createGetQuery(Map<String, String> fields) {
        StringBuilder query = new StringBuilder(BASE_QUERY);
        if (fields.get("tagName") != null) {
            query.append(TAG_NAME).append('"').append(fields.get("tagName")).append('"');
        }
        if (fields.get("partName") != null) {
            query.append(NAME_PART).append("'%").append(fields.get("partName")).append("%'");
        }
        if (fields.get("partDescription") != null) {
            query.append(DESCRIPTION_PART).append("'%").append(fields.get("partDescription")).append("%'");
        }
        if (fields.get("sortByName") != null) {
            query.append(ORDER_BY_NAME).append(fields.get("sortByName"));
        }
        if (fields.get("sortByDate") != null) {
            query.append(ORDER_BY_DATE).append(fields.get("sortByDate"));
        }
        return query.toString();
    }
}
