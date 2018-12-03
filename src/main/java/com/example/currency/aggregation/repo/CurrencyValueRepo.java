package com.example.currency.aggregation.repo;

import com.example.currency.aggregation.entity.Bank;
import com.example.currency.aggregation.entity.CurrencyActionType;
import com.example.currency.aggregation.entity.CurrencyValue;
import com.example.currency.aggregation.entity.NationalCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyValueRepo extends JpaRepository<CurrencyValue,Long> {
    List<CurrencyValue> getByTypeAndBankAndSellingValueAndDisabled(NationalCurrency type, Bank bank, CurrencyActionType sellingValue, boolean disabled);
    List<CurrencyValue> getByTypeAndBankAndSellingValueAndDisabledAndOperationAllowed(NationalCurrency type, Bank bank, CurrencyActionType sellingValue, boolean disabled, boolean operationAllowed);
    List<CurrencyValue> getByTypeAndBankAndDisabled(NationalCurrency type, Bank bank, boolean disabled);
    List<CurrencyValue> getByBankAndSellingValueAndDisabled(Bank bank, CurrencyActionType sellingValue, boolean disabled);
    List<CurrencyValue> getByBankAndDisabled(Bank bank, boolean disabled);
    List<CurrencyValue> getByTypeAndSellingValueAndDisabledAndOperationAllowedOrderByValueAsc(NationalCurrency type, CurrencyActionType sellingValue, boolean disabled, boolean operationAllowed);
    List<CurrencyValue> getByTypeAndSellingValueAndDisabledAndOperationAllowedOrderByValueDesc(NationalCurrency type, CurrencyActionType sellingValue, boolean disabled, boolean operationAllowed);
    List<CurrencyValue> getByDisabled(boolean disabled);
}
