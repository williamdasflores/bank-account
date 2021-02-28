INSERT INTO TB_CUSTOMER(DOCUMENT_NUMBER, FIRST_NAME, LAST_NAME, PHONE_NUMBER, EMAIL, ADDRESS_ID, ACCOUNT_NUMBER)
VALUES(:documentNumber, :firstName, :lastName, :phoneNumber,
:email, :address.id, :checkingAccount.accountNumber);