package com.practice.splitwise.dtos.requests;

import com.practice.splitwise.data.Amount;
import com.practice.splitwise.data.Expense;
import com.practice.splitwise.data.Spender;
import com.practice.splitwise.data.SubExpense;
import com.practice.splitwise.data.enums.Category;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class InsertExpenseDTO extends BaseInsertExpenseDTO implements Serializable {

    private List<Long> beneficiaryList;

    @Builder
    public InsertExpenseDTO(Long addedByPersonId, Long groupId, Category category, Amount amount, String name, List<Long> spenderList, List<Long> beneficiaryList) {
        super(addedByPersonId, groupId, category, amount, name, spenderList);
        this.beneficiaryList = beneficiaryList;
    }
}
