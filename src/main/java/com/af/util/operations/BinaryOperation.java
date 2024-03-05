package com.af.util.operations;

public enum BinaryOperation {
    DIVISION,
    MULTIPLICATION,
    SUBTRACTION,
    ADDITION;

    public static BinaryOperation[] getOperations() {
        return BinaryOperation.values();
    }
}
