INSERT INTO TB_ADDRESS(CITY, STATE, POSTAL_CODE, INFO)
VALUES('São Paulo', 'SP', '03345-222', 'Rua Coelho Lisboa, 44');

INSERT INTO TB_ACCOUNT(BALANCE_ACCOUNT, DATE_INCLUDE) VALUES(0, CURRENT_DATE);

INSERT INTO TB_CUSTOMER(DOCUMENT_NUMBER, FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL, ADDRESS_ID, ACCOUNT_NUMBER)
VALUES(1123123, 'William', 'Flores', 1, 'test@test.com.br', (SELECT ADDRESS_ID FROM TB_ADDRESS),
(SELECT ACCOUNT_NUMBER FROM TB_ACCOUNT));