package org.example.Exceptions;

public class CentralBankExceptions extends Exception {

    private CentralBankExceptions(String msg) {
        super(msg);


    }

    public static CentralBankExceptions WrongIdException(String msg) {
        return new CentralBankExceptions(msg);
    }

    public static CentralBankExceptions WrongDaysException(String msg) {
        return new CentralBankExceptions(msg);
    }
}