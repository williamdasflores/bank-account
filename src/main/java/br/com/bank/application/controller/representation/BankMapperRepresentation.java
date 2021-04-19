package br.com.bank.application.controller.representation;

import br.com.bank.domain.domain.Address;
import br.com.bank.domain.domain.CheckingAccount;
import br.com.bank.domain.domain.Customer;
import br.com.bank.domain.domain.Transaction;
import br.com.bank.v1.representation.*;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class BankMapperRepresentation {

    public Customer mapperCustomer(@Valid CustomerRepresentation customerRepresentation,
                                                          @Valid AddressRepresentation addressRepresentation) {
        Address address = new Address();
        address.setCity(addressRepresentation.getCity());
        address.setState(addressRepresentation.getState().toString());
        address.setPostalCode(addressRepresentation.getPostalCode());
        address.setInformation(address.getInformation());

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setDocumentNumber(Long.valueOf(customerRepresentation.getDocumentNumber()));
        customer.setFirstName(customerRepresentation.getName());
        customer.setLastName(customerRepresentation.getLastName());
        customer.setPhoneNumber(Long.valueOf(customerRepresentation.getPhoneNumber()));
        customer.setEmail(customerRepresentation.getEmail());

        return customer;
    }

    public CheckingAccountRepresentation mapperCheckingAccountRepresentation(CheckingAccount checkingAccount) {
        CheckingAccountRepresentation representation = new CheckingAccountRepresentation();
        representation.setAccountNumber(checkingAccount.getAccountNumber());
        representation.setBalanceAccount(checkingAccount.getBalance());
        return representation;
    }

    public Customer mapperSenderToCustomer(TransferRepresentation representation) {
        Customer sender = new Customer();
        sender.setDocumentNumber(representation.getSender().getDocumentNumber());
        sender.setFirstName(representation.getSender().getName());
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(representation.getSender().getAccountNumber());
        sender.setCheckingAccount(account);
        return sender;
    }

    public Customer mapperPayeeToCustomer(TransferRepresentation representation) {
        Customer payee = new Customer();
        payee.setDocumentNumber(representation.getPayee().getDocumentNumber());
        payee.setFirstName(representation.getPayee().getName());
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(representation.getPayee().getAccountNumber());
        payee.setCheckingAccount(account);
        return payee;
    }

    public TransactionRepresentation mapperTransactionRepresentation(Transaction transaction) {
        TransactionRepresentation representation = new TransactionRepresentation();
        representation.setUuid(transaction.getUuid());
        representation.setDateTransaction(transaction.getDateTransaction().toString());
        representation.setAmount(transaction.getAmount());
        return representation;
    }

    public Customer mapperToDeposit(DepositRepresentation representation) {
        Customer payee = new Customer();
        payee.setDocumentNumber(representation.getPayee().getDocumentNumber());
        payee.setFirstName(representation.getPayee().getName());
        CheckingAccount account = new CheckingAccount();
        account.setAccountNumber(representation.getPayee().getAccountNumber());
        payee.setCheckingAccount(account);
        return payee;
    }

}
