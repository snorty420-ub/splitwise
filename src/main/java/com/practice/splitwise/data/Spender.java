package com.practice.splitwise.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@Table
public class Spender implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long expenseId;
	private Person person;
	private double amount;
	private boolean isSpender;

}
