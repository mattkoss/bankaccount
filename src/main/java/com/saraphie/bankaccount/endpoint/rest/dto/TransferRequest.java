package com.saraphie.bankaccount.endpoint.rest.dto;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TransferRequest {

    private AccountId sourceAccount;
    private AccountId targetAccount;

    private BigDecimal amount;

    private TransferRequest() {
    }

    public TransferRequest(AccountId sourceAccount, AccountId targetAccount, BigDecimal amount) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public AccountId getSourceAccount() {
        return sourceAccount;
    }

    public AccountId getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).append("sourceAccount", sourceAccount)
                .append("targetAccount", targetAccount).append("amount", amount).toString();
    }
}
