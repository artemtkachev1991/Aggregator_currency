package com.example.currency.aggregation.repo;

import com.example.currency.aggregation.entity.NationalCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NationalCurrencyRepo extends JpaRepository<NationalCurrency, Integer> {
    NationalCurrency getByShortName(String shortName);

    @Query(value = "SELECT MAX(order_sequence) FROM nationalcurrency", nativeQuery = true)
    Integer getLastOrder();
}
