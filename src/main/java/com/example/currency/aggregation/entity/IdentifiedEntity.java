package com.example.currency.aggregation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
@MappedSuperclass
abstract class IdentifiedEntity<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, insertable = false, updatable = false, nullable = false, name = "id")
    T id;
    @Temporal(TemporalType.TIMESTAMP)
    Date changed;
    @Column(nullable = false)
    Boolean disabled;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Date getChanged() {
        return changed;
    }

    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
