package org.example.Entities;

import org.example.Exceptions.*;
import org.example.Models.*;

import java.util.Optional;
import java.util.Stack;

public class Bank {

    private final String _name;

    private final int _id;

    private Double _debitRate;

    private Double _creditRate;

    private Stack<Double> _depositSum;

    private Stack<Double> _depositRate;

    private final Stack<User> _users;

    private final Stack<IAccount> _accounts;

    private final Double _unverifiedLimit;

    private final int _depositDuration;

    /**
     * @param name название
     * @param id его номер в системе
     * @param debitRate ставка дебетового счёта
     * @param creditRate ставка кредитового счёта
     * @param depositSum различные "уровни" депозитового счёта
     * @param depositRate ставки по различным "уровням" депозитового счёта
     * @param unverifiedLimit максимальная сумма на операции для неподтверждённого пользователя
     * @param depositDuration длительность депозитного счёта
     * @throws BankExceptions
     * @return сгенерированный банк
     */
    public Bank(String name,
                int id,
                Double debitRate,
                Double creditRate,
                Stack<Double> depositSum,
                Stack<Double> depositRate,
                Double unverifiedLimit,
                int depositDuration) throws BankExceptions {
        if (name == null) {
            throw BankExceptions.NullNameException("Tried to create Bank with null name");
        }
        this._id = id;
        if ((unverifiedLimit < 0)) {
            throw BankExceptions.WrongLimitException("Tried to create Bank with wrong unverified user operation limit");
        }

        if ((debitRate < 0)) {
            throw BankExceptions.WrongRatesException("Tried to create Bank with wrong Debit rate");
        }

        if ((creditRate < 0)) {
            throw BankExceptions.WrongRatesException("Tried to create Bank with wrong Credit rate");
        }

        this._name = name;
        this._unverifiedLimit = unverifiedLimit;
        this._debitRate = debitRate;
        this._creditRate = creditRate;
        this._depositSum = depositSum;
        this._depositRate = depositRate;
        this._users = new Stack<>();
        this._accounts = new Stack<>();
        this._depositDuration = depositDuration;
    }

    /**
     * @return название банка
     */
    public String GetName() {
        return _name;
    }

    /**
     * @return порядковый номер банка
     */
    public int GetId() {
        return _id;
    }

    /**
     * @return ставку на дебетовый счёт
     */
    public Double GetDebitRate() {
        return _debitRate;
    }

    /**
     * @return ставку на кредитный счёт
     */
    public Double GetCreditRate() {
        return _creditRate;
    }

    /**
     * @return ставки на депозитный счёт
     */
    public Stack<Double> GetDepositRate() {
        return _depositRate;
    }

    /**
     * @return суммы на различных "уровнях" счёта
     */
    public Stack<Double> GetDepositSum() {
        return _depositSum;
    }

    /**
     * @return пользователей, использующих данный банк
     */
    public Stack<User> GetUsers() {
        return _users;
    }

    /**
     * @return срок открытия депозитного счёта
     */
    public int GetDepositDuration() {
        return _depositDuration;
    }

    /**
     * @param user пользователь, которого нужно подключить к банку
     * @throws BankExceptions
     */
    public void AddUser(User user) throws BankExceptions {
        if ((user == null)){
            throw BankExceptions.NullUserException("Tried to add null user to bank");
        }

        this._users.add(user);
    }

    /**
     * @param user пользователь, которого нужно отключить от банка
     * @throws BankExceptions
     */
    public void RemoveUser(User user) throws BankExceptions {
        if ((user == null)) {
            throw BankExceptions.NullUserException("Tried to remove null user from bank");
        }

        if ((!this._users.contains(user))) {
            throw BankExceptions.NullUserException("Tried to remove user which is not in this bank");
        }

        if( _accounts.stream().filter(acc -> user.GetAccounts().contains(acc) && acc.GetSum() != 0).count() != 0 ){
            throw BankExceptions.NonNullAccountsException("Tried to remove User with not 0 sum on accounts in bank");
        }
        this._users.remove(user);
    }

    /**
     * @param account тип открываемого счёта
     * @param user пользователь, которому нужно открыть счёт
     * @throws BankExceptions
     */
    public void AddAccount(IAccount account, User user) throws BankExceptions {
        if ((!this._users.contains(user))) {
            throw BankExceptions.NullUserException("Tried to create Account for User not in this Bank");
        }

        if ((account == null)) {
            throw BankExceptions.NullAccountException("Tried to create null Account");
        }

        this._accounts.add(account);
        try {
            user.AddAccount(account);
        } catch (UserExceptions e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param debitRate новая ставка по дебетовому счёту
     * @param creditRate новая ставка по кредитному счёту
     * @param depositSum новые суммы по депозитному счёту
     * @param depositRate новые ставки по депозитному счёту
     * @throws BankExceptions
     */
    public void ChangeRates(Double debitRate, Double creditRate, Stack<Double> depositSum, Stack<Double> depositRate) throws BankExceptions {
        if ((debitRate < 0 || creditRate < 0 || depositRate == null)) {
            throw BankExceptions.WrongRatesException("Tried to change account rates to wrong");
        }

        this._debitRate = debitRate;
        this._creditRate = creditRate;
        this._depositRate = depositRate;
        this._depositSum = depositSum;
        for(IAccount account : this._accounts) {
            Optional<User> curUser = _users.stream()
                    .filter(user -> user.GetAccounts().contains(account))
                    .findFirst();
            if (curUser.isEmpty()) {
                throw BankExceptions.NullUserException("Tried to change Rates for Account of wrong User");
            }
            try {
                account.ChangeRates(this, curUser.get());
            } catch (AccountExceptions e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * @param account счёт, который нужно закрыть
     * @param user пользователь, чей счёт нужно закрыть
     * @throws BankExceptions
     */
    public void CloseAccount(IAccount account, User user) throws BankExceptions {
        if ((!this._users.contains(user))) {
            throw BankExceptions.NullUserException("Tried to close Account of User not in this Bank");
        }

        if ((!this._accounts.contains(account))) {
            throw BankExceptions.NullAccountException("Tried to close Account not belonging to this Bank");
        }

        if ((!user.GetAccounts().contains(account))) {
            throw BankExceptions.NullAccountException("Tried to close Account of User who dont have it");
        }

        if ((account.GetSum() != 0)) {
            throw BankExceptions.NonNullAccountsException("Tried to close User's account with not 0 sum");
        }

        this._accounts.remove(account);
        try {
            user.RemoveAccount(account);
        } catch (UserExceptions e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param pastDay день, с которого мы стартуем
     * @param daysPassed сколько дней пропустить
     * @throws BankExceptions
     */
    public void UpdateDays(int pastDay, int daysPassed) throws BankExceptions {
        for (int i = 0; (i < daysPassed); i++) {
            for (IAccount account : this._accounts) {
                account.ApplyRate();
                if (((account.GetDuration() != -1) &&
                        ((account.GetStartDay() + account.GetDuration()) == (pastDay + i)))) {
                    Optional<User> connectedUser = _users.stream()
                            .filter(user -> user.GetAccounts().contains(account))
                            .findFirst();
                    if (connectedUser.isEmpty()) {
                        throw BankExceptions.NullUserException("Tried to update Account of invalid User");
                    }
                    try {
                        connectedUser.get().AddMoney(account.GetSum());
                    } catch (UserExceptions e) {
                        throw new RuntimeException(e);
                    }
                    account.ChangeSum(0.0);
                    this.CloseAccount(account, connectedUser.get());
                }

                if (((daysPassed % (30 + account.GetStartDay())) == 0)) {
                    try {
                        account.ExecuteRate();
                    } catch (AccountExceptions e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        }

    }
}