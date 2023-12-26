package com.practice.splitwise.dtos.requests;

import com.practice.splitwise.data.Amount;
import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Spender;
import com.practice.splitwise.data.enums.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InsertExpenseDTO implements Serializable {
    private Long addedByPersonId;
    private Long groupId;
    private Category category;
    private Amount amount;
    private List<Long> spenderList;
    private List<Long> beneficiaryList;
}
