package com.example.currency.aggregation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true, exclude = {"type", "bank"})
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
public class CurrencyValue extends IdentifiedEntity{
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private NationalCurrency type;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private Bank bank;
    @Digits(integer=7, fraction=2)
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private CurrencyActionType sellingValue;
    private Boolean operationAllowed;


    @Override
    public String toString() {
        return "CurrencyValue{" +
                "type=" + (type!=null?type.getShortName():"null") +
                ", bank=" + (bank!=null?bank.getDisplayName():"null") +
                ", value=" + value +
                ", sellingValue=" + (sellingValue!=null?sellingValue.toString():"null") +
                ", operationAllowed=" + operationAllowed +
                ", id=" + id +
                ", changed=" + changed +
                ", disabled=" + disabled +
                '}';
    }

    public NationalCurrency getType() {
        return type;
    }

    public void setType(NationalCurrency type) {
        this.type = type;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyActionType getSellingValue() {
        return sellingValue;
    }

    public void setSellingValue(CurrencyActionType sellingValue) {
        this.sellingValue = sellingValue;
    }

    public Boolean getOperationAllowed() {
        return operationAllowed;
    }

    public void setOperationAllowed(Boolean operationAllowed) {
        this.operationAllowed = operationAllowed;
    }

}
