SELECT COUNT(t1) FROM TB_ACCOUNT t1
INNER JOIN TB_CUSTOMER t2 ON t1.ACCOUNT_NUMBER = t2.ACCOUNT_NUMBER
WHERE t1.ACCOUNT_NUMBER = :accountNumber AND t2.DOCUMENT_NUMBER = :document