package org.example.Entities;

import org.example.Exceptions.*;
import org.example.Models.*;

import java.util.Stack;

public class User {

    private final String _name;

    private final String _surname;

    private Double _money;

    private final String _address;

    private final String _passport;

    private final Stack<IAccount> _accounts;

    private boolean _isVerified;

    private final Stack<IOperation> _operations;

    /**
     * @param name имя пользователя
     * @param surname фамилия пользователя
     * @param address адрес проживания пользователя
     * @param passport паспортные данные пользователя
     */
    public User(String name, String surname, String address, String passport) {
        this._name = name;
        this._surname = surname;
        this._address = address;
        this._passport = passport;
        this._accounts = new Stack<>();
        this._money = 0.0;
        this._isVerified = false;
        this._operations = new Stack<>();
    }

    /**
     * @return имя пользователя
     */
    public String GetName() {
        return _name;
    }

    /**
     * @return фамилию пользователя
     */
    public String GetSurname() {
        return _surname;
    }

    /**
     * @return адрес проживания пользователя
     */
    public String GetAddress() {
        return _address;
    }

    /**
     * @return паспортные данные пользователя
     */
    public String GetPassport() {
        return _passport;
    }

    /**
     * @return деньги пользователя
     */
    public Double GetMoney() {
        return _money;
    }

    /**
     * @return счета пользователя
     */
    public Stack<IAccount> GetAccounts() {
        return _accounts;
    }

    /**
     * @return подтверждён ли пользователь
     */
    public boolean IsVerified() {
        return _isVerified;
    }

    /**
     * @return операции пользователя
     */
    public Stack<IOperation> GetOperations() {
        return _operations;
    }

    /**
     * @param id порядковый номер счёта
     * @return счёт пользователя
     */
    public final IAccount GetAccount(int id) {
        return this._accounts.get(id);
    }

    /**
     * подтверждение пользователя
     */
    public final void Verify() {
        this._isVerified = true;
    }

    /**
     * @param account счёт пользователя
     * @throws UserExceptions
     */
    public final void AddAccount(IAccount account) throws UserExceptions {
        if ((account == null)) {
            throw UserExceptions.NullAccountException("Tried to add null Account to User");
        }

        this._accounts.add(account);
    }

    /**
     * @param account счёт пользователя
     * @throws UserExceptions
     */
    public final void RemoveAccount(IAccount account) throws UserExceptions {
        if ((account == null || (!this._accounts.contains(account)))) {
            throw UserExceptions.NullAccountException("Tried to remove wrong Account");
        }

        this._accounts.remove(account);
    }

    /**
     * @param money денежная сумма
     * @throws UserExceptions
     */
    public final void AddMoney(Double money) throws UserExceptions {
        if ((money < 0)) {
            throw UserExceptions.WrongMoneyException("Tried to add wrong amount of money");
        }

        this._money = (this._money + money);
    }
}