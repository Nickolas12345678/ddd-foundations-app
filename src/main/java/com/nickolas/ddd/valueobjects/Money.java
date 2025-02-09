package com.nickolas.ddd.valueobjects;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class Money {
    private final String currency;
    private final BigDecimal amount;


    public Money(String currency, BigDecimal amount) {
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        this.currency = currency;
        this.amount = amount;
    }



    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must be the same to add");
        }
        return new Money(this.currency, this.amount.add(other.amount));
    }


    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currencies must be the same to subtract");
        }
        return new Money(this.currency, this.amount.subtract(other.amount));
    }


    public boolean equals(Money other) {
        return this.currency.equals(other.currency) && this.amount.compareTo(other.amount) == 0;
    }


    public String toDisplayFormat() {
        NumberFormat format = NumberFormat.getInstance(Locale.US);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return currency + " " + format.format(amount);
    }


    public Money multiply(int quantity) {
        return new Money(this.currency, this.amount.multiply(BigDecimal.valueOf(quantity)));
    }


    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return currency.equals(money.currency) && amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
