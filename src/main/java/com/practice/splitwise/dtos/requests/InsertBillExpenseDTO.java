package com.practice.splitwise.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.splitwise.data.Amount;
import com.practice.splitwise.data.SubExpense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertBillExpenseDTO extends BaseInsertExpenseDTO implements Serializable {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BillExpenseDTO implements Serializable {
        private List<Long> beneficiaryList;
        @JsonProperty("subExpense")
        private SubExpenseDTO subExpenseDTO;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SubExpenseDTO implements Serializable {

            private Amount amount ;
            private String name;
        }
    }

    private List<BillExpenseDTO> billExpenses;
}
