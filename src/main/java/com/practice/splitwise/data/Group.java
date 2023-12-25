package com.practice.splitwise.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="GroupTable")
public class Group {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany
	private List<Expense> expenseList;
	@OneToMany
	private List<Person> personList;
	public void mergeGroup(Group group) {
//		expenseList.addAll(group.getExpenseList());
//		personList.addAll(group.getMembers());
	}

	public void removeMembers(Person person) {
//		personList = personList.parallelStream()
//				.filter(personOfList-> personOfList.getId() != person.getId())
//				.collect(Collectors.toList());
	}

	public void addMembers(Person person) {
		personList.add(person);
	}

	public void removeExpense(Expense expense) {
		expenseList = expenseList.parallelStream()
				.filter(expenseOfList-> expenseOfList.getId() != expense.getId())
				.collect(Collectors.toList());
	}
}
