package com.example.currency.aggregation.repo;

import com.example.currency.aggregation.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepo extends JpaRepository<Bank, Integer> {
    Bank getByDisplayName(String displayName);
}
