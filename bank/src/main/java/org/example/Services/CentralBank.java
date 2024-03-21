package org.example.Services;

import org.example.Entities.Bank;
import org.example.Entities.User;
import org.example.Exceptions.*;
import org.example.Models.IAccount;
import org.example.Models.Operation;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private final List<Bank> _banks;
    private final List<User> _users;
    private int _day;

    public CentralBank() {
        _banks = new ArrayList<>();
        _users = new ArrayList<>();
        _day = 0;
    }

    /**
     * @return все банки
     */
    public List<Bank> GetBanks() {
        return _banks;
    }

    /**
     * @return всех пользователей
     */
    public List<User> GetUsers() {
        return _users;
    }

    /**
     * @return текущий день
     */
    public int GetDay() {
        return _day;
    }

    /**
     * @param bank банк, который нужно добавить в систему
     */
    public void AddBank(Bank bank) {
        _banks.add(bank);
    }

    /**
     * @param user пользователь, которого нужно добавить в систему
     */
    public void AddUser(User user) {
        _users.add(user);
    }

    /**
     * @param user пользователь, открывающий счёт
     * @param account открываемый счёт
     */
    public void AddAccount(User user, IAccount account){
        try {
            account.GetConnectedBank().AddAccount(account, user);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param user пользователь, закрывающий счёт
     * @param account закрываемый счёт
     */
    public void RemoveAccount(User user, IAccount account){
        try {
            account.GetConnectedBank().CloseAccount(account, user);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param skippedDays количество пропускаемых дней
     */
    public void SkipDays(int skippedDays){
        if (skippedDays <= 0) {
            try {
                throw CentralBankExceptions.WrongDaysException("Tried to skip wrong amount of days");
            } catch (CentralBankExceptions e) {
                throw new RuntimeException(e);
            }
        }

        _banks.forEach(bank -> {
            try {
                bank.UpdateDays(_day,skippedDays);
            } catch (BankExceptions e) {
                throw new RuntimeException(e);
            }
        });

        _day += skippedDays;
    }

    /**
     * @param startUser пользователь, отправляющий деньги
     * @param finUser пользователь, принимающий деньги
     * @param sum сумма операции
     * @param startAccount счёт списания
     * @param finAccount счёт зачислеия
     */
    public void ExecuteOperation(User startUser, User finUser, Double sum, IAccount startAccount, IAccount finAccount){
        Operation operation;
        try {
            operation = new Operation(startUser, finUser, sum, startAccount, finAccount);
        } catch (OperationExceptions e) {
            throw new RuntimeException(e);
        }
        try {
            operation.ExecuteOperation();
        } catch (OperationExceptions e) {
            throw new RuntimeException(e);
        }
        startUser.GetOperations().add(operation);
    }

    /**
     * @param user пользователь, совершивший операцию
     * @param id порядковый номер операции
     */
    public void UndoOperation(User user, int id){
        if (id < 0) {
            user.GetOperations();
        }

        try {
            user.GetOperations().get(id).UndoOperation();
        } catch (OperationExceptions e) {
            throw new RuntimeException(e);
        }
        user.GetOperations().remove(user.GetOperations().get(id));
    }
}