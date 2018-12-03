package com.example.currency.aggregation.service;

import com.example.currency.aggregation.dto.CurrencyDTO;
import com.example.currency.aggregation.entity.Bank;
import com.example.currency.aggregation.entity.CurrencyActionType;
import com.example.currency.aggregation.entity.CurrencyValue;
import com.example.currency.aggregation.entity.NationalCurrency;
import com.example.currency.aggregation.repo.BankRepo;
import com.example.currency.aggregation.repo.CurrencyRepo;
import com.example.currency.aggregation.repo.CurrencyValueRepo;
import com.example.currency.aggregation.repo.NationalCurrencyRepo;
import com.example.currency.aggregation.support.StaticMessages;
import com.example.currency.aggregation.support.WrongIncomingDataExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrencyService implements CurrencyRepo {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);
    private static final Comparator<CurrencyValue> dateComparator = Comparator.comparing(x -> x.getChanged());

    @Autowired
    private CurrencyValueRepo currencyValueRepo;

    @Autowired
    private NationalCurrencyRepo nationalCurrencyRepo;

    @Autowired
    private BankRepo bankRepo;


    @Override
    public List<CurrencyDTO> getSpecificCurrency(String currencyShortName, boolean isBuying, boolean ascendByPrice) throws WrongIncomingDataExeption {
        List<CurrencyValue> result;
        NationalCurrency nationalCurrency = nationalCurrencyRepo.getByShortName(currencyShortName);
        if (nationalCurrency==null) {
            String message = String.format(StaticMessages.MESSAGE_ILLEGAL_CURRENCY_NAME, currencyShortName);
            LOG.info(message);
            throw new WrongIncomingDataExeption(message);
        }
        CurrencyActionType actionType = isBuying ? CurrencyActionType.BUYING : CurrencyActionType.SELLING;

        if (ascendByPrice) {
            result = currencyValueRepo.getByTypeAndSellingValueAndDisabledAndOperationAllowedOrderByValueAsc(nationalCurrency, actionType, false, true);
        } else {
            result = currencyValueRepo.getByTypeAndSellingValueAndDisabledAndOperationAllowedOrderByValueDesc(nationalCurrency, actionType, false, true);
        }

        return result.stream().map(this::convert).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public CurrencyDTO persistCurrency(CurrencyDTO newValue) throws WrongIncomingDataExeption {

        NationalCurrency currency = nationalCurrencyRepo.getByShortName(newValue.getName());
        if (currency==null){
            NationalCurrency newOne = new NationalCurrency();
            newOne.setShortName(newValue.getName());
            newOne.setChanged(new Date());
            newOne.setDisabled(false);
            newOne.setOrder(nationalCurrencyRepo.getLastOrder());
            currency = nationalCurrencyRepo.save(newOne);
        }

        Bank bank = bankRepo.getByDisplayName(newValue.getBank());
        if (bank==null){
            Bank newBank = new Bank();
            newBank.setDisplayName(newValue.getBank());
            newBank.setChanged(new Date());
            newBank.setDisabled(false);
            bank = bankRepo.save(newBank);
        }

        CurrencyActionType actionType;
        try {
            actionType = CurrencyActionType.valueOf(newValue.getAction());
        } catch (IllegalArgumentException e) {
            throw new WrongIncomingDataExeption("Unknown action: "+newValue.getAction());
        }
        Boolean allowed = newValue.getAllowed();

        BigDecimal value;
        if (newValue.getValue()==null || newValue.getValue().isEmpty()) {
            Optional<CurrencyValue> valueOptional = currencyValueRepo.getByTypeAndBankAndSellingValueAndDisabledAndOperationAllowed(currency, bank, actionType, false, true).stream().min(dateComparator);
            if (valueOptional.isPresent()){
                value = valueOptional.get().getValue();
            } else {
                value = new BigDecimal(0);
            }
        } else if (!newValue.getValue().equals(StaticMessages.EMPTY_VALUE)){
            value = new BigDecimal(newValue.getValue());
        } else {
            value = new BigDecimal(0);
        }

        try {
            List<CurrencyValue> previousList = currencyValueRepo.getByTypeAndBankAndSellingValueAndDisabled(currency, bank, actionType, false);

            for (CurrencyValue currentPrevious : previousList) {
                currentPrevious.setDisabled(true);
            }

            CurrencyValue toPersist = new CurrencyValue();
            toPersist.setBank(bank);
            if (value.intValue()==0){
                toPersist.setOperationAllowed(false);
            } else {
                toPersist.setOperationAllowed(allowed);
            }
            toPersist.setDisabled(false);
            toPersist.setSellingValue(actionType);
            toPersist.setType(currency);
            toPersist.setValue(value);
            toPersist.setChanged(new Date());

            //historyService.persistHistory(null, toPersist);
            CurrencyValue result = currencyValueRepo.save(toPersist);
            return convert(result);

        } catch (RuntimeException e) {
            String message = String.format(StaticMessages.MESSAGE_ILLEGAL_CURRENCY_CREATION, newValue);
            LOG.warn(message, e);
            throw new WrongIncomingDataExeption(message);
        }
    }


    @Transactional
    @Override
    public void persistCurrencyList(List<CurrencyValue> valueList) {
        LOG.info("Saving list to the database");

        currencyValueRepo.saveAll(valueList);
    }


    @Override
    public List<CurrencyDTO> changeSpecificCurrencyAllowanceByBank(String bankName, String shortName, String action, Boolean allow, boolean delete) throws WrongIncomingDataExeption {
        Bank bank = bankRepo.getByDisplayName(bankName);
        if (bank==null){
            throw new WrongIncomingDataExeption("Can't find bank with name: "+bankName);
        }

        NationalCurrency currency = nationalCurrencyRepo.getByShortName(shortName);

        CurrencyActionType actionType = null;
        if (action!=null) {
            try {
                actionType = CurrencyActionType.valueOf(action);
            } catch (IllegalArgumentException e) {
                throw new WrongIncomingDataExeption("Unknown action: "+action);
            }
        }

        List<CurrencyValue> toProcessList;
        if (currency!=null){
            if (actionType!=null){
                toProcessList = currencyValueRepo.getByTypeAndBankAndSellingValueAndDisabled(currency, bank, actionType, false);
            } else {
                toProcessList = currencyValueRepo.getByTypeAndBankAndDisabled(currency, bank, false);
            }
        } else {
            if (actionType!=null){
                toProcessList = currencyValueRepo.getByBankAndSellingValueAndDisabled(bank, actionType, false);
            } else {
                toProcessList = currencyValueRepo.getByBankAndDisabled(bank, false);
            }
        }

        if (delete) {
            toProcessList.stream().filter(x-> !x.getDisabled()).forEach(x->x.setDisabled(true));
        } else if (allow!=null && allow) {
            toProcessList.stream().filter(x-> !x.getOperationAllowed()).forEach(x->x.setOperationAllowed(true));
        } else if (allow!=null && !allow) {
            toProcessList.stream().filter(CurrencyValue::getOperationAllowed).forEach(x->x.setOperationAllowed(false));
        }

        currencyValueRepo.saveAll(toProcessList);

        return toProcessList.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<CurrencyValue> getAllData() {
        return currencyValueRepo.getByDisabled(false);
    }

    private CurrencyDTO convert(CurrencyValue value) {
        return new CurrencyDTO(value);
    }
}