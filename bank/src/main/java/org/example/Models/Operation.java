package org.example.Models;

import org.example.Entities.*;
import org.example.Exceptions.*;

public class Operation implements IOperation {
    private final User _startUser;
    private final IAccount _startAccount;
    private final User _finUser;
    private final IAccount _finAccount;
    private final Double _sum;
    private Boolean _isExecuted;
    private Boolean _isUndone;

    /**
     * @param startUser пользователь, отправляющий деньги
     * @param finUser пользователь, принимающий деньги
     * @param sum сумма операции
     * @param startAccount счёт списания
     * @param finAccount счёт зачисления
     * @throws OperationExceptions
     */
    public Operation(User startUser, User finUser, Double sum, IAccount startAccount, IAccount finAccount) throws OperationExceptions {
        if (!startAccount.GetConnectedBank().GetUsers().contains(startUser) || !finAccount.GetConnectedBank().GetUsers().contains(finUser)) {
                throw OperationExceptions.UserNotInBankException(
                        "Tried to do operation for user not in bank");
        }

        if (!startUser.GetAccounts().contains(startAccount) ||
                !finUser.GetAccounts().contains(finAccount)) {
                throw OperationExceptions.UserDontHaveAccountException(
                        "Tried to do operation between account that user dont have");
        }

        _sum = sum;
        _startUser = startUser;
        _finUser = finUser;
        _startAccount = startAccount;
        _finAccount = finAccount;
        _isExecuted = false;
        _isUndone = false;
    }

    /**
     * @return пользователь, отправляющий деньги
     */
    public User GetStartUser() {
        return _startUser;
    }

    /**
     * @return пользователь, принимающий деньги
     */
    public User GetFinUser() {
        return _finUser;
    }

    /**
     * @return счёт списания
     */
    public IAccount GetStartAccount() {
        return _startAccount;
    }

    /**
     * @return счёт зачисления
     */
    public IAccount GetFinAccount() {
        return _finAccount;
    }

    /**
     * @return сумму операции
     */
    public Double GetSum() {
        return _sum;
    }

    /**
     * выполнение операции
     * @throws OperationExceptions
     */
    public void ExecuteOperation() throws OperationExceptions {
        if (_isExecuted) {
            throw OperationExceptions.UsingOperationAgainException(
                        "Tried to do operation which is already done");
        }

        if (_startAccount == _finAccount) {
            _startAccount.ChangeSum(_startAccount.GetSum() + _sum);
        } else {
            _startAccount.ChangeSum(_startAccount.GetSum() - _sum);
            _finAccount.ChangeSum(_finAccount.GetSum() + _sum);
        }

        _isExecuted = true;
    }

    /**
     * отмена операции
     * @throws OperationExceptions
     */
    public void UndoOperation() throws OperationExceptions {
        if (_isUndone) {
                throw OperationExceptions.UsingOperationAgainException(
                        "Tried to undo operation which is already undone");
        }

        if (_startUser == _finUser && _startAccount == _finAccount) {
            _startAccount.ChangeSum(_startAccount.GetSum() - _sum);
        }

        _isUndone = true;
    }
}
