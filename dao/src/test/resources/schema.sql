CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               LONG          NOT NULL AUTO_INCREMENT,
    name             VARCHAR(45)   NOT NULL UNIQUE,
    description      VARCHAR(45)   NOT NULL UNIQUE,
    price            DECIMAL(3, 2) NOT NULL,
    duration         INT           NOT NULL,
    create_date      TIMESTAMP     NOT NULL,
    last_update_date TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)

);


CREATE TABLE IF NOT EXISTS tag
(
    tag_id   LONG        NOT NULL AUTO_INCREMENT,
    tag_name VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (tag_id)
);

CREATE TABLE IF NOT EXISTS gift_tags
(
    id      LONG NOT NULL AUTO_INCREMENT,
    gift_id INT  NOT NULL,
    tag_id  INT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);