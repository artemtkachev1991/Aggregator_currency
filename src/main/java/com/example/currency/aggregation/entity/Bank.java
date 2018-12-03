package com.example.currency.aggregation.entity;

import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
@EqualsAndHashCode(callSuper = true, exclude = "currencyValueList")
@Entity
public class Bank extends IdentifiedEntity<Integer>{
    private String displayName;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bank")
    List<CurrencyValue> currencyValueList = new ArrayList<>();

    @Override
    public String toString() {
        return "Bank{" +
                "displayName='" + displayName + '\'' +
                ", id=" + id +
                ", changed=" + changed +
                ", disabled=" + disabled +
                '}';
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<CurrencyValue> getCurrencyValueList() {
        return currencyValueList;
    }

    public void setCurrencyValueList(List<CurrencyValue> currencyValueList) {
        this.currencyValueList = currencyValueList;
    }
}
