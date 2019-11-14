package com.saraphie.bankaccount.endpoint.rest.dto;

import com.saraphie.bankaccount.domain.AccountBalance;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TransferResponse {

    private AccountBalance sourceAccountBalance;
    private AccountBalance targetAccountBalance;

    public TransferResponse(AccountBalance sourceAccountBalance, AccountBalance targetAccountBalance) {
        this.sourceAccountBalance = sourceAccountBalance;
        this.targetAccountBalance = targetAccountBalance;
    }

    public AccountBalance getSourceAccountBalance() {
        return sourceAccountBalance;
    }

    public AccountBalance getTargetAccountBalance() {
        return targetAccountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TransferResponse response = (TransferResponse) o;

        return new EqualsBuilder().append(sourceAccountBalance, response.sourceAccountBalance)
                .append(targetAccountBalance, response.targetAccountBalance).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(sourceAccountBalance).append(targetAccountBalance).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("sourceAccountBalance", sourceAccountBalance)
                .append("targetAccountBalance", targetAccountBalance).toString();
    }
}
