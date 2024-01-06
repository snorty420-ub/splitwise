package com.practice.splitwise.data;

import com.practice.splitwise.data.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Expense implements Serializable {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private Long groupId;
	private Long addedBy;
	private Timestamp date;
	private Category category;
	private String amount;




//	@OneToMany(targetEntity=Expense.class)
//	private List<Expense> subExpenseList;
//
//	@OneToMany(targetEntity=Receipt.class)
//	private List<Receipt> receiptList;
//
//	@OneToMany(targetEntity=Spender.class)
//	private List<Spender> spenderList;
//
//	@OneToMany(targetEntity=Spender.class)
//	private List<Spender> beneficiaryList;
//	private void updateMembers() {
//		if(isUpdated){
//			return;
//		}
//		isUpdated = true;
//		for (Expense expense : subExpenseList) {
//			amount.add(expense.getAmount());
//			spenderList.addAll(expense.getSpenderList());
//			beneficiaryList.addAll(expense.getBeneficiaryList());
//		}
//		spenderList = removeDuplicates(spenderList);
//		removeDuplicates(beneficiaryList);
//	}

//	private <T> List<T> removeDuplicates(List<T> list) {
//		return list.stream().distinct().collect(Collectors.toList());
//	}
}
