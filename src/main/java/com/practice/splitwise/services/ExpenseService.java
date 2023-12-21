package com.practice.splitwise.services;

import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Group;
import com.practice.splitwise.data.Person;
import com.practice.splitwise.exceptions.ExpenseNotFoundException;
import com.practice.splitwise.repositories.ExpenseRepository;
import com.practice.splitwise.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private PersonService personService;
    private GroupService groupService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, PersonService personService){
        this.expenseRepository = expenseRepository;
        this.personService = personService;
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(ExpenseNotFoundException::new);
    }

    public List<Expense> getAllExpenses() {
        return Utilities.IterableToList(expenseRepository.findAll());
    }

    public Long insertExpense(Expense expense) {
        return expenseRepository.save(expense).getId();
    }

    public Expense updateExpense(Long id, Expense expense) {
        expense.setId(id);
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getAllExpensesForPerson(Long personId) {
        Person person = personService.getPersonById(personId);
        return expenseRepository.findByAddedBy(person);
    }

    public Expense getExpenseByIdForPerson(Long personId, Long expenseId) {
        Expense expense = getExpenseById(expenseId);
        if(expense.getAddedBy().getId() != personId)
            throw new ExpenseNotFoundException();
        return expense;
    }

    public Long insertExpenseForPerson(Long personId, Expense expense) {
        return insertExpenseForPersonGetObject(personId, expense).getId();
    }

    public Expense updateExpenseForPerson(Long personId, Long expenseid, Expense expense) {
        expense.setId(expenseid);
        return insertExpenseForPersonGetObject(personId, expense);
    }

    public void deleteExpenseForPerson(Long personID, Long expenseId) {
        Expense expense = getExpenseByIdForPerson(personID, expenseId);
        expenseRepository.delete(expense);
    }

    private Expense insertExpenseForPersonGetObject(Long personId, Expense expense) {
        Person person = personService.getPersonById(personId);
        expense.setAddedBy(person);
        return expenseRepository.save(expense);
    }

    public Long insertExpenseForPerson(Long personId, Long groupId, Expense expense) {
        Group group = groupService.getGroupById(groupId);
        Expense expenseWithAddedBy = insertExpenseForPersonGetObject(personId, expense);
        group.addExpense(expenseWithAddedBy);
        return expenseWithAddedBy.getId();
    }
}
