package org.example.Console;

import org.example.Entities.Bank;
import org.example.Entities.User;
import org.example.Exceptions.*;
import org.example.Models.*;
import org.example.Services.CentralBank;

import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class Program {
    private final CentralBank _centralBank;

    private Program(CentralBank centralBank) {
        _centralBank = centralBank;
    }

    public static void main(String[] args) throws BankExceptions, UserExceptions, AccountExceptions, OperationExceptions, CentralBankExceptions {
        var centralBank = new CentralBank();
        int bankId = 1;
        var depositSums = new Stack<Double>();
        depositSums.add(0.0);
        depositSums.add(10000.0);
        var depositRates = new Stack<Double>();
        depositRates.add(0.05);
        depositRates.add(0.04);

        var bank = new Bank("Bank1", bankId++, 0.02, 0.03, depositSums, depositRates, 1000.0, 30);
        var bank2 = new Bank("Bank2", bankId, 0.03, 0.05, depositRates, depositRates, 3000.0, 300);
        centralBank.AddBank(bank);
        centralBank.AddBank(bank2);
        var program = new Program(centralBank);
        program.Execute();
    }

    private void Execute() throws UserExceptions, BankExceptions, AccountExceptions, OperationExceptions, CentralBankExceptions {
        System.out.println("Create Users? - y/n");

        Scanner scanner = new Scanner(System.in);
        String temp = scanner.nextLine();
        while (!Objects.equals(temp, "y") && !Objects.equals(temp, "n")) {
            System.out.println("Error - wrong format , must be just y or n");
            System.out.println("Try again , Create user? - y/n");
            temp = scanner.nextLine();
        }

        while (Objects.equals(temp, "y")) {
            System.out.println("Enter User's name");
            String name = scanner.nextLine();
            if (name.isBlank()) {
                throw UserExceptions.NullNameException("Tried to create User with null name");
            }
            System.out.println("Enter User's surname");
            String surname = scanner.nextLine();
            if (surname == null) {
                throw UserExceptions.NullNameException("Tried to create User with null surname");
            }
            System.out.println("Enter User's address");
            String address = scanner.nextLine();
            if (address == null) {
                throw UserExceptions.NullAddressException(
                        "Tried to create User with null address");
            }
            System.out.println("Enter User's passport");
            String passport = scanner.nextLine();
            if (passport == null) {
                throw UserExceptions.NullPassportException("Tried to create User with null passport");
            }
            var builder = new UserBuilder(name, surname);
            var buildDirector = new BuildDirector(builder);
            if (!Objects.equals(passport, "") && !Objects.equals(address, "")) {
                User user = buildDirector.BuildFullUser(address, passport);
                _centralBank.AddUser(user);
            } else if (Objects.equals(address, "") && Objects.equals(passport, "")) {
                User user = buildDirector.BuildPartUser();
                _centralBank.AddUser(user);
            }

            System.out.println("User created successfully!");
            System.out.println("Do you want to add another user? - y/n");

            temp = scanner.nextLine();
            while (!Objects.equals(temp, "y") && !Objects.equals(temp, "n")) {
                System.out.println("Error - wrong format , must be just y or n");
                System.out.println("Try again , Create user? - y/n");
                temp = scanner.nextLine();
            }
        }

        while (true) {
            System.out.println("""
                    What to do?\s
                    1 - Create new bank account for User\s
                    2 - Delete User's bank account\s
                    3 - Do an operation
                    4 - Undo User's operation\s
                    5 - Skip some days\s
                    6 - Exit""");
            temp = scanner.nextLine();
            if (Objects.equals(temp, "1")) {
                System.out.println("Create for which User? Enter User id");
                int userId = scanner.nextInt();
                System.out.println("In which Bank? Enter Bank id");
                int bankId = scanner.nextInt();
                System.out.println("Which Account? \n 1 - debit \n 2 - credit \n 3 - deposit");
                int temp2 = scanner.nextInt();
                System.out.println("Which start sum?");
                Double sum = scanner.nextDouble();
                if (temp2 == 1) {
                    User curUser = _centralBank.GetUsers().get(userId);
                    Bank curBank = _centralBank.GetBanks().get(bankId);
                    curBank.AddUser(curUser);
                    _centralBank.AddAccount(
                            curUser, new DebitAccount(
                                    curBank, "Debit in " + curBank.GetName(), curBank.GetDebitRate(), sum, _centralBank.GetDay()));
                } else if (temp2 == 2) {
                    User curUser = _centralBank.GetUsers().get(userId);
                    Bank curBank = _centralBank.GetBanks().get(bankId);
                    curBank.AddUser(curUser);
                    _centralBank.AddAccount(
                            curUser, new CreditAccount(
                                    curBank, "Credit in " + curBank.GetName(), curBank.GetCreditRate(), sum, _centralBank.GetDay()));
                } else if (temp2 == 3) {
                    User curUser = _centralBank.GetUsers().get(userId);
                    Bank curBank = _centralBank.GetBanks().get(bankId);
                    curBank.AddUser(curUser);
                    _centralBank.AddAccount(
                            curUser, new DepositAccount(
                                    curBank, "Deposit in " + curBank.GetName(), curBank.GetDepositRate(), curBank.GetDepositSum(), sum, curBank.GetDepositDuration(), _centralBank.GetDay()));
                }
            } else if (Objects.equals(temp, "2")) {
                System.out.println("Delete for which User? Enter User id");
                int userId = scanner.nextInt();
                System.out.println("Which account? Enter Account id");
                int accountId = scanner.nextInt();
                _centralBank.RemoveAccount(_centralBank.GetUsers().get(userId), _centralBank.GetUsers().get(userId).GetAccount(accountId));
            } else if (Objects.equals(temp, "3")) {
                System.out.println("What type of operation? \n 1 - Withdraw \n 2 - Deposit \n 3 - Transfer");
                int temp2 = scanner.nextInt();
                if (temp2 == 1) {
                    System.out.println("For which user? Enter id");
                    int userId = Integer.parseInt(System.in.toString());
                    System.out.println("From which account? Enter id");
                    int accId = Integer.parseInt(System.in.toString());
                    System.out.println("What sum?");
                    double curSum = scanner.nextDouble();
                    User curUser = _centralBank.GetUsers().get(userId);
                    _centralBank.ExecuteOperation(
                            curUser, curUser, -curSum, curUser.GetAccount(accId), curUser.GetAccount(accId));
                } else if (temp2 == 2) {
                    System.out.println("For which user? Enter id");
                    int userId = scanner.nextInt();
                    System.out.println("To which account? Enter id");
                    int accId = scanner.nextInt();
                    System.out.println("What sum?");
                    Double curSum = scanner.nextDouble();
                    User curUser = _centralBank.GetUsers().get(userId);
                    _centralBank.ExecuteOperation(
                            curUser, curUser, curSum, curUser.GetAccount(accId), curUser.GetAccount(accId));
                } else if (temp2 == 3) {
                    System.out.println("For which user? Enter id");
                    int userId = scanner.nextInt();
                    System.out.println("From which account? Enter id");
                    int accId = scanner.nextInt();
                    System.out.println("To which user? Enter id");
                    int user2Id = scanner.nextInt();
                    System.out.println("To which account? Enter id");
                    int acc2Id = scanner.nextInt();
                    System.out.println("What sum?");
                    Double curSum = scanner.nextDouble();
                    User curUser = _centralBank.GetUsers().get(userId);
                    User curUser2 = _centralBank.GetUsers().get(user2Id);
                    _centralBank.ExecuteOperation(
                            curUser, curUser2, curSum, curUser.GetAccount(accId), curUser2.GetAccount(acc2Id));
                }
            } else if (Objects.equals(temp, "4")) {
                System.out.println("Which User's operation? Enter id");
                int userId = scanner.nextInt();
                System.out.println("Which id? enter id");
                int opId = scanner.nextInt();
                _centralBank.UndoOperation(_centralBank.GetUsers().get(userId), opId);
            } else if (Objects.equals(temp, "5")) {
                System.out.println("How many days to skip?");
                int skippedDays = scanner.nextInt();
                _centralBank.SkipDays(skippedDays);
            } else if (Objects.equals(temp, "6")) {
                return;
            }
        }
    }
}