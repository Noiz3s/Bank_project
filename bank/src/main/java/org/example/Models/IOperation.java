package org.example.Models;

import org.example.Entities.*;
import org.example.Exceptions.OperationExceptions;

public interface IOperation {
    User GetStartUser();

    User GetFinUser();

    IAccount GetStartAccount();

    IAccount GetFinAccount();

    Double GetSum();

    void ExecuteOperation() throws OperationExceptions;

    void UndoOperation() throws OperationExceptions;
}