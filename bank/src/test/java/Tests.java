import org.example.Entities.*;
import org.example.Exceptions.*;
import org.example.Models.*;
import org.example.Services.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class Tests {
    @Test
    public void AccountAddedCorrectly() {
        var centralBank = new CentralBank();
        int bankId = 1;
        var depositSums = new Stack<Double>();
        depositSums.add(0.0);
        depositSums.add(10000.0);
        var depositRates = new Stack<Double>();
        depositRates.add(0.04);
        depositRates.add(0.05);
        Bank bank;
        try {
            bank = new Bank("Bank1", bankId, 0.02, 0.03, depositSums, depositRates, 1000.0, 30);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
        centralBank.AddBank(bank);

        var builder = new UserBuilder("Egor", "Mironenko");
        var buildDirector = new BuildDirector(builder);
        User user = buildDirector.BuildFullUser("Pr-kt Prosveschenia, 131", "1311 131311");
        centralBank.AddUser(user);

        try {
            bank.AddUser(user);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
        DebitAccount account;
        try {
            account = new DebitAccount(bank, "Debit1", bank.GetDebitRate(), 100.0, centralBank.GetDay());
        } catch (AccountExceptions e) {
            throw new RuntimeException(e);
        }
        centralBank.AddAccount(user, account);
        Assert.assertTrue(user.GetAccounts().contains(account));
        Assert.assertTrue(bank.GetUsers().contains(user));
    }

    @Test
    public void TransferOperationDoneCorrectly() {
        var centralBank = new CentralBank();
        int bankId = 1;
        var depositSums = new Stack<Double>();
        depositSums.add(0.0);
        depositSums.add(10000.0);
        var depositRates = new Stack<Double>();
        depositRates.add(0.04);
        depositRates.add(0.05);
        Bank bank;
        try {
            bank = new Bank("Bank1", bankId, 0.02, 0.03, depositSums, depositRates, 1000.0, 30);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
        centralBank.AddBank(bank);

        var builder = new UserBuilder("Egor", "Mironenko");
        var buildDirector = new BuildDirector(builder);
        User user = buildDirector.BuildFullUser(
                "Pr-kt Prosveschenia, 131", "1311 131311");
        centralBank.AddUser(user);

        var newBuilder = new UserBuilder("Viktoriya", "Mironenko");
        var newbuildDirector = new BuildDirector(newBuilder);
        User user2 = newbuildDirector.BuildFullUser(
                "Pr-kt Prosveschenia, 131", "1001 100101");
        centralBank.AddUser(user2);

        try {
            bank.AddUser(user);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
        DebitAccount account;
        try {
            account = new DebitAccount(bank, "Debit1", bank.GetDebitRate(), 100.0, centralBank.GetDay());
        } catch (AccountExceptions e) {
            throw new RuntimeException(e);
        }
        centralBank.AddAccount(user, account);

        try {
            bank.AddUser(user2);
        } catch (BankExceptions e) {
            throw new RuntimeException(e);
        }
        DebitAccount account2;
        try {
            account2 = new DebitAccount(bank, "Debit1", bank.GetDebitRate(), 100.0, centralBank.GetDay());
        } catch (AccountExceptions e) {
            throw new RuntimeException(e);
        }
        centralBank.AddAccount(user2, account2);

        centralBank.ExecuteOperation(user, user2, 50.0, account, account2);
        Assert.assertEquals(50.0, account.GetSum(), 0.0);
        Assert.assertEquals(150.0, account2.GetSum(), 0.0);
    }
}