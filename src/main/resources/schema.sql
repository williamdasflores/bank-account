DROP TABLE IF EXISTS TB_ADDRESS;
DROP TABLE IF EXISTS TB_ACCOUNT;
DROP TABLE IF EXISTS TB_CUSTOMER;

CREATE TABLE TB_ADDRESS(
    ADDRESS_ID INT AUTO_INCREMENT PRIMARY KEY,
    CITY VARCHAR(100) NOT NULL,
    STATE CHAR(2) NULL,
    POSTAL_CODE VARCHAR(10) NOT NULL,
    INFO VARCHAR(255) NULL
);

CREATE TABLE TB_ACCOUNT(
    ACCOUNT_NUMBER INT AUTO_INCREMENT PRIMARY KEY,
    BALANCE_ACCOUNT DECIMAL(18,2) NOT NULL,
    DATE_INCLUDE DATE NOT NULL,
    DATE_UPDATE DATE NULL
);

CREATE TABLE TB_CUSTOMER(
    CUSTOMER_ID INT AUTO_INCREMENT PRIMARY KEY,
    DOCUMENT_NUMBER BIGINT NOT NULL,
    FIRST_NAME VARCHAR(80) NOT NULL,
    LAST_NAME VARCHAR(80) NOT NULL,
    PHONE_NUMBER BIGINT NULL,
    EMAIL VARCHAR(200) NULL,
    ADDRESS_ID INT NOT NULL,
    ACCOUNT_NUMBER INT NOT NULL,
    CONSTRAINT FK_ADDRESSID FOREIGN KEY (ADDRESS_ID) REFERENCES TB_ADDRESS (ADDRESS_ID),
    CONSTRAINT FK_ACCOUNT_NUMBER FOREIGN KEY (ACCOUNT_NUMBER) REFERENCES TB_ACCOUNT (ACCOUNT_NUMBER)
);

CREATE TABLE TB_LIMIT_TRANSACTION(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    LIMIT_VALUE DECIMAL(18,2) NOT NULL
);
