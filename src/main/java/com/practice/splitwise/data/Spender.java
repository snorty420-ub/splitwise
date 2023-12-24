package com.practice.splitwise.data;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@Table
@Builder
public class Spender implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long expenseId;
	private Long fromUserId;
	private Long toUserId;
	private String amount;
	private boolean isSpender;

}
