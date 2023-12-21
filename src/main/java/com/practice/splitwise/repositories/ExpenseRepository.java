package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByAddedBy(Person person);
}
