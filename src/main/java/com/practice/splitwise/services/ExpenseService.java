package com.practice.splitwise.services;

import com.practice.splitwise.data.*;
import com.practice.splitwise.dtos.requests.InsertExpenseDTO;
import com.practice.splitwise.exceptions.ExpenseNotFoundException;
import com.practice.splitwise.repositories.ExpenseRepository;
import com.practice.splitwise.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        if(expense.getAddedBy().longValue() != personId)
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
        expense.setAddedBy(person.getId());
        return expenseRepository.save(expense);
    }

    public Long insertExpenseForPerson(Long personId, Long groupId, Expense expense) {
        Group group = groupService.getGroupById(groupId);
        Expense expenseWithAddedBy = insertExpenseForPersonGetObject(personId, expense);
        group.addExpense(expenseWithAddedBy);
        return expenseWithAddedBy.getId();
    }

    public Long insertExpenseForPerson(InsertExpenseDTO insertExpenseDTO) {
        // create and save expense object
        Long expenseID = createExpense(insertExpenseDTO);
        // calculation of splits
        Map<Pair<Long,Long>,Integer> amountSplitMap = calculateShares(insertExpenseDTO);
        // create and save spender and beneficiary
        // update friendship
        //
        return expenseID;
    }

    private Map<Pair<Long, Long>, Integer> calculateShares(InsertExpenseDTO insertExpenseDTO) {
        Map<Pair<Long, Long>, Integer> amountSplitMap = new HashMap<>();
        List<Spender> spenderList = insertExpenseDTO.getSpenderList();
        List<Spender> beneficiaryList = insertExpenseDTO.getBeneficiaryList();
        double amount = insertExpenseDTO.getAmount().getAmount();
        double rough = amount/spenderList.size();

        return amountSplitMap;
    }

    private Long createExpense(InsertExpenseDTO insertExpenseDTO) {
        return expenseRepository.save(Expense.builder()
                .addedBy(insertExpenseDTO.getAddedByPersonId())
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .category(insertExpenseDTO.getCategory())
                .amount(insertExpenseDTO.getAmount())
                .build()).getId();
    }

}
