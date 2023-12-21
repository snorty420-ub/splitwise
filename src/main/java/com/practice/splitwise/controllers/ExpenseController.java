package com.practice.splitwise.controllers;

import com.practice.splitwise.data.Expense;
import com.practice.splitwise.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getExpenses(@RequestHeader Long personId){
        return expenseService.getAllExpensesForPerson(personId);
    }

    @GetMapping("/{expenseId}")
    public Expense getExpenseById(@RequestHeader Long personId, @PathVariable Long expenseId){
        return expenseService.getExpenseByIdForPerson(personId, expenseId);
    }

    @PostMapping
    public Long insertExpense(@RequestHeader Long personId, @RequestBody Expense expense){
        return expenseService.insertExpenseForPerson(personId, expense);
    }

    @PostMapping("/group/{groupId}")
    public Long insertExpenseToGroup(@RequestHeader Long personId, @PathVariable Long groupId, @RequestBody Expense expense){
        return expenseService.insertExpenseForPerson(personId, groupId,  expense);
    }

    @PutMapping("/{expenseId}")
    public Expense updateExpense(@RequestHeader Long personId, @PathVariable Long expenseId, @RequestBody Expense expense){
        return expenseService.updateExpenseForPerson(personId, expenseId, expense);
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@RequestHeader Long personId, @PathVariable Long expenseId){
        expenseService.deleteExpenseForPerson(personId, expenseId);
    }
}
