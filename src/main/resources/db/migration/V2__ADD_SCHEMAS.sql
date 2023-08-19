CREATE TABLE tag
(
    id                 SERIAL PRIMARY KEY,
    version            INT          NOT NULL,
    name               VARCHAR(100) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE person
(
    id                 SERIAL PRIMARY KEY,
    version            INT          NOT NULL,
    first_name         VARCHAR(100) NOT NULL,
    last_name          VARCHAR(100) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);

CREATE TABLE item
(
    id                 SERIAL PRIMARY KEY,
    version            INT       NOT NULL,
    status             VARCHAR(15),
    description        VARCHAR(255),
    created_date       TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL,
    assignee_id        INT       NOT NULL,
    CONSTRAINT fk_person FOREIGN KEY (assignee_id) REFERENCES person (id)
);

CREATE TABLE item_tag
(
    id      SERIAL PRIMARY KEY,
    item_id SERIAL REFERENCES item (id),
    tag_id  SERIAL REFERENCES tag (id)
);