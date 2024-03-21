package org.example.Models;

import org.example.Entities.*;
import org.example.Exceptions.*;

public class CreditAccount implements IAccount {
    private final Bank _bank;
    private final int _startDay;
    private final Double _negativeBorder;
    private final String _name;
    private Double _sum;
    private Double _rate;
    private Double _rateSum;

    /**
     * @param bank банк, в котором открывается счёт
     * @param name название счёта
     * @param rate ставка по счёту
     * @param sum стартовая сумма на счёт
     * @param startDay день открытия счёта
     * @throws AccountExceptions
     */
    public CreditAccount(Bank bank, String name, Double rate, Double sum, int startDay) throws AccountExceptions {
        if (rate <= 0) {
            throw AccountExceptions.WrongRateException("Tried to create CreditAccount with wrong rate");
        }
        if (sum < 0) {
            throw AccountExceptions.WrongSumException("Tried to create CreditAccount with negative sum");
        }
        _bank = bank;
        if (bank == null) {
            throw AccountExceptions.NullBankException("Tried to create Account in null bank");
        }
        _name = name;
        if (name == null) {
            throw AccountExceptions.NullNameException("Tried to create Account with null name");
        }
        _rate = rate;
        _sum = sum;
        _negativeBorder = -sum;
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
     * @return проценты по счёту
     */
    public Double GetRate() {
        return _rate;
    }

    /**
     * @return минимальная сумма денег
     */
    public Double GetNegativeBorder() {
        return _negativeBorder;
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
        int _duration = -1;
        return _duration;
    }

    public void ApplyRate() {
        _rateSum -= _sum * _rate;
    }

    public void ExecuteRate() {
        if (_sum >= _negativeBorder) return;
        ChangeSum(_sum + _rateSum);
        _rateSum = 0.0;
    }

    /**
     * @param newSum новая денежная сумма
     */
    public void ChangeSum(Double newSum) {
        _sum = newSum;
    }

    /**
     * @param bank банк, в котором открыт счёт
     * @param user пользователь, открывший данный счёт
     * @throws AccountExceptions
     */
    public void ChangeRates(Bank bank, User user) throws AccountExceptions {
        if (bank != _bank || !user.GetAccounts().contains(this)) {
            throw AccountExceptions.WrongChangeRatesDataException("Tried to change rates with wrong Bank or User");
        }
        _rate = bank.GetCreditRate();
    }
}
