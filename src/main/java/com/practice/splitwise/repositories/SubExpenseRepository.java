package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Person;
import com.practice.splitwise.data.SubExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubExpenseRepository extends JpaRepository<SubExpense, Long> {
}
