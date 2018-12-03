package com.example.currency.aggregation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true, exclude = {"currencyValueList"})
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
public class NationalCurrency extends IdentifiedEntity<Integer>{
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<CurrencyValue> getCurrencyValueList() {
        return currencyValueList;
    }

    public void setCurrencyValueList(List<CurrencyValue> currencyValueList) {
        this.currencyValueList = currencyValueList;
    }

    @Column(unique = true)
    private String shortName;
    @Column(name="order_sequence")
    private Integer order;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "type")
    List<CurrencyValue> currencyValueList = new ArrayList<>();

    @Override
    public String toString() {
        return "NationalCurrency{" +
                "shortName='" + shortName + '\'' +
                ", order=" + order +
                ", id=" + id +
                ", changed=" + changed +
                ", disabled=" + disabled +
                '}';
    }
}
