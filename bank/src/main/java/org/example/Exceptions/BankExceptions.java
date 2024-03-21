package org.example.Exceptions;

public class BankExceptions extends Exception {

    private BankExceptions(String msg) {
        super(msg);
    }

    public static BankExceptions NullNameException(String msg) {
        return new BankExceptions(msg);
    }

    public static BankExceptions NullUserException(String msg) {
        return new BankExceptions(msg);
    }

    public static BankExceptions WrongRatesException(String msg) {
        return new BankExceptions(msg);
    }

    public static BankExceptions NullAccountException(String msg) {
        return new BankExceptions(msg);
    }

    public static BankExceptions WrongLimitException(String msg) {
        return new BankExceptions(msg);
    }

    public static BankExceptions NonNullAccountsException(String msg) {
        return new BankExceptions(msg);
    }
}