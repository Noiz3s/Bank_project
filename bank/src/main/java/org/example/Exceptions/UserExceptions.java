package org.example.Exceptions;

public class UserExceptions extends Exception {

    private UserExceptions(String msg) {
        super(msg);


    }

    public static UserExceptions NullNameException(String msg) {
        return new UserExceptions(msg);
    }

    public static UserExceptions NullAddressException(String msg) {
        return new UserExceptions(msg);
    }

    public static UserExceptions NullPassportException(String msg) {
        return new UserExceptions(msg);
    }

    public static UserExceptions NullAccountException(String msg) {
        return new UserExceptions(msg);
    }

    public static UserExceptions WrongMoneyException(String msg) {
        return new UserExceptions(msg);
    }
}