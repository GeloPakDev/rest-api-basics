INSERT INTO tag (tag_name)
VALUES ('tagName1');

INSERT INTO tag (tag_name)
VALUES ('tagName2');

INSERT INTO tag (tag_name)
VALUES ('tagName3');

INSERT INTO tag (tag_name)
VALUES ('tagName4');

INSERT INTO tag (tag_name)
VALUES ('tagName5');



INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate1', 'description1', 1.12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate2', 'description2', 2.22, 2, '2022-10-05 11:15:10', '2022-10-05 11:15:10');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate3', 'description3', 3.33, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate4', 'description4', 4.44, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate5', 'description5', 5.55, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO gift_tags (gift_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (2, 2);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (3, 4);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (4, 3);

INSERT INTO gift_tags (gift_id, tag_id)
VALUES (5, 1);


