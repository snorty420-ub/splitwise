package com.practice.splitwise.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Spender implements Serializable {
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)

	private Person person;
	private double amount;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
