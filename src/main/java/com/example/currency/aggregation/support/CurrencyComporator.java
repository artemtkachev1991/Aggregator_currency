package com.example.currency.aggregation.support;

import com.example.currency.aggregation.entity.CurrencyValue;

import java.util.Comparator;

public class CurrencyComporator implements Comparator<CurrencyValue> {
    private  boolean ascend;

    public CurrencyComporator(boolean ascend) {
        this.ascend = ascend;
    }

    @Override
    public int compare(CurrencyValue o1, CurrencyValue o2) {
        if (ascend){
            return o1.getValue().compareTo(o2.getValue());
        } else {
            return o2.getValue().compareTo(o1.getValue());
        }
    }
}
