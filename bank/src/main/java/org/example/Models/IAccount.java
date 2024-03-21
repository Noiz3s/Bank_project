package org.example.Models;

import org.example.Entities.*;
import org.example.Exceptions.AccountExceptions;

public interface IAccount {
    Bank GetConnectedBank();

    Double GetSum();

    Double GetRate();

    int GetStartDay();

    String GetName();

    int GetDuration();

    void ChangeSum(Double newSum);

    void ExecuteRate() throws AccountExceptions;

    void ApplyRate();

    void ChangeRates(Bank bank, User user) throws AccountExceptions;
}
