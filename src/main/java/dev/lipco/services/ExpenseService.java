package dev.lipco.services;

import dev.lipco.entities.Expense;
import dev.lipco.entities.LoginCredentials;
import dev.lipco.entities.Avenger;

import java.util.Set;

public interface ExpenseService {

    Avenger login(LoginCredentials loginCredentials) throws IllegalAccessException;

    Avenger getAvenger(Avenger avenger);

    Expense newRequest(Avenger avenger, Expense expense);
    Set<Expense> getAllSubmissions(Avenger avenger) throws IllegalAccessException;
    Expense reviewRequest(Avenger avenger, Expense expense) throws IllegalAccessException;
}
