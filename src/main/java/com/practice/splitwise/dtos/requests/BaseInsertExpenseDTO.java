package com.practice.splitwise.dtos.requests;

import com.practice.splitwise.data.Amount;
import com.practice.splitwise.data.enums.Category;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInsertExpenseDTO implements Serializable {
    private Long addedByPersonId;
    private Long groupId;
    private Category category;
    private Amount amount;
    private String name;
    private List<Long> spenderList;

}
