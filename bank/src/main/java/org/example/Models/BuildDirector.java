package org.example.Models;

import org.example.Entities.*;

public class BuildDirector {
    private UserBuilder _builder;

    /**
     * @param builder строитель (паттерн)
     */
    public BuildDirector(UserBuilder builder) {
        _builder = builder;
    }

    /**
     * @return пользователь без некоторых данных
     */
    public User BuildPartUser() {
        return _builder.GetPartInfo();
    }

    /**
     * @param address адрес проживания пользователя
     * @param passport паспортные данные пользователя
     * @return пользователь с полными данными
     */
    public User BuildFullUser(String address, String passport) {
        return _builder.GetFullInfo(address, passport);
    }
}

