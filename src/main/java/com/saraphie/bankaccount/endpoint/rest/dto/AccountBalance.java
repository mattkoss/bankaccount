package com.saraphie.bankaccount.endpoint.rest.dto;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AccountBalance {

    private BigDecimal amount;
    private String currency;

    private AccountBalance() {
    }

    public AccountBalance(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).append("amount", amount)
                .append("currency", currency).toString();
    }
}
