package com.practice.splitwise.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Person implements Serializable {

	private static long counter=0L;
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	private String password;



	public Person(String firstName, String middleName, String lastName, String userName, String password) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.id = counter;

//		groupList = new ArrayList<>();
//		expenseList = new ArrayList<>();

		counter++;
	}

	public Person() {
	}

	public void setName(String firstName, String lastName){
		 setName(firstName,"",lastName);
	}

	public void setName(String firstName, String middleName, String lastName){
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public String getName(){
		if("".equals(middleName))
			return String.format("%s %s",firstName,lastName);

		return String.format("%s %s %s",firstName,middleName,lastName);
	}



//	public void addExpense(Group group, Expense expense){
//		if(!checkGroupPresent(group)) {
//			Utilities.printError(String.format("Group %s not present in %s", group, this));
//			return;
//		}
//		addToExpenseList(expense);
//	}

//	private void addToExpenseList(Expense expense) {
//		expenseList.add(expense);
//	}
//
//	private boolean checkGroupPresent(Group group){
//		Stream<Group> groupStream= groupList.stream();
//		Group g = groupStream.filter((groupListItem) -> {
//			if (groupListItem == group)
//				return true;
//			return false;
//		})
//				.findAny()
//				.orElse(null);
//		return g != null;
//	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}