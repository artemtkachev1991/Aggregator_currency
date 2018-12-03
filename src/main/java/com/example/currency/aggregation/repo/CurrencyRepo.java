package com.example.currency.aggregation.repo;

import com.example.currency.aggregation.dto.CurrencyDTO;
import com.example.currency.aggregation.entity.CurrencyValue;
import com.example.currency.aggregation.support.WrongIncomingDataExeption;

import java.util.List;

public interface CurrencyRepo {
    List<CurrencyDTO> getSpecificCurrency(String currencyShortName, boolean isBuying, boolean ascendByPrice)
            throws WrongIncomingDataExeption;

    CurrencyDTO persistCurrency(CurrencyDTO newValue) throws WrongIncomingDataExeption;

    void persistCurrencyList(List<CurrencyValue> valueList);

    List<CurrencyDTO> changeSpecificCurrencyAllowanceByBank(String bankName, String shortName,
                                                            String action, Boolean allow, boolean delete)
            throws WrongIncomingDataExeption;

    List<CurrencyValue> getAllData();
}
