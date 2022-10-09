package com.epam.esm;

public final class QuerySQL {
    public QuerySQL() {
    }

    //Queries for GIFT_CERTIFICATE TABLE
    public static final String SELECT_GIFT_CERTIFICATE_BY_ID = """
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
            WHERE gc.id = ?""";

    public static final String SELECT_ALL_GIFT_CERTIFICATES = """
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

    public static final String DELETE_GIFT_CERTIFICATE_BY_ID = """
            DELETE
            FROM gift_certificate
            WHERE id = ?
            """;

    public static final String CREATE_GIFT_CERTIFICATE = """
            INSERT INTO gift_certificate
            (name, description, price, duration, create_date, last_update_date)
            VALUES(?,?,?,?,?,?)
            """;

    //Queries for TAG TABLE
    public static final String SELECT_TAG_BY_ID = """
            SELECT tag_id,
                   tag_name
            FROM tag
            WHERE tag_id = ?
            """;

    public static final String SELECT_TAG_BY_NAME = """
            SELECT tag_id,
                   tag_name
            FROM tag
            WHERE tag_name = ?
            """;

    public static final String SELECT_ALL_TAGS = """
            SELECT tag_id,
                   tag_name
            FROM tag
            """;

    public static final String CREATE_TAG = """
            INSERT INTO tag
            (tag_name)
            VALUES(?)
            """;

    public static final String DELETE_TAG = """
            DELETE
            FROM tag
            WHERE tag_id = ?
            """;

    //Mixed queries
    public static final String SELECT_GIFT_BY_TAG_NAME = """            
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
            WHERE t.tag_name = ?
            """;

    public static final String SELECT_GIFT_BY_NAME_PART = """
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
            WHERE gc.name LIKE CONCAT('%',?,'%')
            """;
    public static final String SELECT_GIFT_BY_DESCRIPTION_PART = """
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
            WHERE gc.description LIKE CONCAT('%',?,'%')
            """;

    public static final String SORT_GIFT_BY_NAME_DESC = """
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
            ORDER BY gc.name DESC
            """;

    public static final String SORT_GIFT_BY_NAME_ASC = """
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
            ORDER BY gc.name ASC
            """;

    public static final String SORT_GIFT_BY_DATE_DESC = """
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
            ORDER BY gc.create_date DESC
            """;

    public static final String SORT_GIFT_BY_DATE_ASC = """
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
            ORDER BY gc.create_date ASC
            """;

    public static final String SORT_GIFT_BY_DATE_AND_NAME_DESC = """
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
            ORDER BY gc.create_date DESC, gc.name DESC;
            """;

    public static final String UPDATE_GIFT_CERTIFICATE = """
            UPDATE gift_certificate
            SET name=?,
                description=?,
                price=?,
                duration=?,
                last_update_date=?
            WHERE id =?
            """;

    public static final String CREATE_GIFT_WITH_TAG = """
            INSERT INTO gift_tags
            (gift_id , tag_id)
            VALUES(?,?)
            """;

    public static final String UPDATE_GIFT_CERTIFICATE_TAG = """
            UPDATE gift_tags
            SET tag_name=?
            WHERE id=?
            """;
    public static final String DELETE_GIFT_CERTIFICATE_TAG_BY_ID = """
            DELETE
            FROM gift_tags
            WHERE gift_id = ?
            """;
}