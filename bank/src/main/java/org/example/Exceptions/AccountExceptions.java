package org.example.Exceptions;

public class AccountExceptions extends Exception {

    private AccountExceptions(String msg) {
        super(msg);
    }

    public static AccountExceptions WrongRateException(String msg) {
        return new AccountExceptions(msg);
    }

    public static AccountExceptions WrongSumException(String msg) {
        return new AccountExceptions(msg);
    }

    public static AccountExceptions DepositWithdrawException(String msg) {
        return new AccountExceptions(msg);
    }

    public static AccountExceptions NullBankException(String msg) {
        return new AccountExceptions(msg);
    }

    public static AccountExceptions WrongChangeRatesDataException(String msg) {
        return new AccountExceptions(msg);
    }

    public static AccountExceptions NullNameException(String msg) {
        return new AccountExceptions(msg);
    }
}