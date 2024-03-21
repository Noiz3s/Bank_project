package org.example.Models;

import org.example.Entities.*;

public class UserBuilder {
    private String _name;
    private String _surName;

    /**
     * @param name имя пользователя
     * @param surName фамилия пользователя
     */
    public UserBuilder(String name, String surName) {
        _name = name;
        _surName = surName;
    }

    /**
     * @param name новое имя пользователя
     * @param surName новая фамилия пользователя
     */
    public void ChangeName(String name, String surName) {
        _name = name;
        _surName = surName;
    }

    /**
     * @return частичную информацию о пользователе
     */
    public User GetPartInfo() {
        return new User(_name, _surName, "", "");
    }

    /**
     * @param address адрес проживания пользователя
     * @param passport паспортные данные пользователя
     * @return полную информацию о пользователе
     */
    public User GetFullInfo(String address, String passport) {
        return new User(_name, _surName, address, passport);
    }
}