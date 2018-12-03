package com.example.currency.aggregation.support;

import com.example.currency.aggregation.entity.CurrencyValue;

import java.util.function.Predicate;

public class DataContainerCheck implements Predicate<CurrencyValue> {
    private String currencyName;

    public DataContainerCheck(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public boolean test(CurrencyValue currencyValue) {
        if (!currencyValue.getType().getShortName().equals(currencyName) || currencyValue.getDisabled() || !currencyValue.getOperationAllowed() || currencyValue.getBank().getDisabled() || currencyValue.getType().getDisabled()){
            return false;
        }
        return true;
    }
}
