CREATE TABLE POST (
    ID SERIAL PRIMARY KEY,
    LINK VARCHAR(255) UNIQUE,
    NAME VARCHAR(255),
    TEXT VARCHAR(2018),
    CREATED TIMESTAMP
);