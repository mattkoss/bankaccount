package com.saraphie.bankaccount.endpoint.rest.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccountId {

    private String sortCode;
    private String accountNumber;

    private AccountId() {
    }

    public AccountId(String sortCode, String accountNumber) {
        this.sortCode = sortCode;
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).append("sortCode", sortCode)
                .append("accountNumber", accountNumber).toString();
    }
}
