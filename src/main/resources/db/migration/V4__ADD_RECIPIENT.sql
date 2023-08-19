CREATE TABLE IF NOT EXISTS recipient
(
    id        VARCHAR(36) PRIMARY KEY DEFAULT gen_random_uuid(),
    channel   VARCHAR(255) NOT NULL,
    address   VARCHAR(1024),
    full_name VARCHAR(1000)
);

ALTER TABLE notification ADD CONSTRAINT fk_notification_recipient_uid
    FOREIGN KEY (recipient_uid) REFERENCES recipient(id);