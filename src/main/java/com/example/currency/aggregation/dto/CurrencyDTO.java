package com.example.currency.aggregation.dto;

import com.example.currency.aggregation.entity.CurrencyValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDTO {
    public CurrencyDTO(String name, String bank, String action, String value, Boolean allowed) {
        this.name = name;
        this.bank = bank;
        this.action = action;
        this.value = value;
        this.allowed = allowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    private String name;
    private String bank;
    private String action;
    private String value;
    private Boolean allowed;

    public CurrencyDTO(CurrencyValue value) {
        this.name = value.getType().getShortName();
        this.bank = value.getBank().getDisplayName();
        this.action = value.getSellingValue().name();
        this.value = value.getValue().toPlainString();
        this.allowed = value.getOperationAllowed();
    }

    public CurrencyDTO() {
    }
}
