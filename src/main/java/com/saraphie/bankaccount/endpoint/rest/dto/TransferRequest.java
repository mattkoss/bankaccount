package com.saraphie.bankaccount.endpoint.rest.dto;

import java.math.BigDecimal;

import com.saraphie.bankaccount.domain.AccountId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TransferRequest that = (TransferRequest) o;

        return new EqualsBuilder().append(sourceAccount, that.sourceAccount).append(targetAccount, that.targetAccount)
                .append(amount, that.amount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sourceAccount).append(targetAccount).append(amount).toHashCode();
    }
}
