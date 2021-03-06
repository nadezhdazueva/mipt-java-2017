package ru.mipt.java2016.homework.g594.plahtinskiy.task1;

import ru.mipt.java2016.homework.base.task1.ParsingException;

import java.util.Stack;

/**
 * Created by VadimPl on 13.10.16.
 */
public final class OpenParanthesO extends Operations {

    @Override
    protected int priority() {
        return 0;
    }

    @Override
    protected void makeOperation(Stack<Number> results) throws ParsingException {
        throw new ParsingException("Logical error: open parenthesis can't make any operation");
    }

    @Override
    public void addLexeme(Stack<Number> results, Stack<Operations> operations) throws ParsingException {
        operations.push(this);
    }

}