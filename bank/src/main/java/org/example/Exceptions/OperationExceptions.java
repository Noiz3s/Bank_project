package org.example.Exceptions;

public class OperationExceptions extends Exception {

    private OperationExceptions(String msg) {
        super(msg);
    }

    public static OperationExceptions UserNotInBankException(String msg) {
        return new OperationExceptions(msg);
    }

    public static OperationExceptions UserDontHaveAccountException(String msg) {
        return new OperationExceptions(msg);
    }

    public static OperationExceptions UsingOperationAgainException(String msg) {
        return new OperationExceptions(msg);
    }
}
