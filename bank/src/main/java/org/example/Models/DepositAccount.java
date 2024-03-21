package org.example.Models;

import org.example.Entities.*;
import org.example.Exceptions.*;

import java.util.List;
import java.util.Optional;

public class DepositAccount implements IAccount {
    private final Bank _bank;
    private final int _duration;
    private final int _startDay;
    private final String _name;
    private Double _sum;
    private List<Double> _rates;
    private final List<Double> _sums;
    private Double _curRate;
    private Double _rateSum;

    /**
     * @param bank название банка, в котором открывается счёт
     * @param name название счёта
     * @param rates ставки по счёту
     * @param sums суммы, разделяющие "уровни счёта"
     * @param sum начальная сумма на счету
     * @param duration длительность счёта
     * @param startDay день открытия счёта
     * @throws AccountExceptions
     */
    public DepositAccount(Bank bank,
                          String name,
                          List<Double> rates,
                          List<Double> sums,
                          Double sum,
                          int duration,
                          int startDay) throws AccountExceptions {
        if (sum < 0) {
                throw AccountExceptions.WrongSumException("Tried to create DepositAccount with negative sum");
        }

        _bank = bank;
        if (bank == null) {
                throw AccountExceptions.NullBankException("Tried to create Account in null bank");
        }
        _rates = rates;
        if (rates == null) {
            throw AccountExceptions.WrongRateException("Tried to create DepositAccount with null rates");
        }
        _name = name;
        if (name == null) {
            throw AccountExceptions.NullNameException("Tried to create Account with null name");
        }
        _sum = sum;
        _sums = sums;
        _curRate = GetCurRate();
        _duration = duration;
        _startDay = startDay;
        _rateSum = 0.0;
    }

    /**
     * @return сумму на счету
     */
    public Double GetSum() {
        return _sum;
    }

    /**
     * @return ставку по текущей сумме на счету
     */
    public Double GetRate() {
        return _curRate;
    }

    /**
     * @return день открытия счёта
     */
    public int GetStartDay() {
        return _startDay;
    }

    /**
     * @return банк, в котором открыт счёт
     */
    public Bank GetConnectedBank() {
        return _bank;
    }

    /**
     * @return название счёта
     */
    public String GetName() {
        return _name;
    }

    /**
     * @return длительность счёта
     */
    public int GetDuration() {
        return _duration;
    }

    /**
     * @param newSum новая сумма на счету
     */
    public void ChangeSum(Double newSum) {
        _sum = newSum;
        _curRate = GetCurRate();
    }

    public void ApplyRate() {
        _rateSum += _sum * _curRate;
    }

    public void ExecuteRate() {
        ChangeSum(_sum + _rateSum);
        _rateSum = 0.0;
    }

    /**
     * @param bank банк, в котором открыт счёт
     * @param user пользователь, открывший счёт
     * @throws AccountExceptions
     */
    public void ChangeRates(Bank bank, User user) throws AccountExceptions {
        if (bank != _bank || !user.GetAccounts().contains(this)) {
            throw AccountExceptions.WrongChangeRatesDataException("Tried to change rates with wrong Bank or User");
        }
        _rates = bank.GetDepositRate();
        _curRate = GetCurRate();
    }

    /**
     * @return текущую ставку
     */
    private Double GetCurRate(){
        Optional<Double> temp =_sums.stream().filter(sum -> sum <= _sum).findFirst();
        return temp.orElse(null);
    }
}