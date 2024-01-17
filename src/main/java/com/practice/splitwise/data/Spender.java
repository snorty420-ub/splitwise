package com.practice.splitwise.data;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * stores leni deni amount info in groupid expense and subexpense level
 */
@Entity
@Data
@Table
@Builder
public class Spender implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long groupId;
	private Long expenseId;
	// TODO: take care of pairing (from,to)=(to,from)
	private Long fromUserId;
	private Long toUserId;
	private String amount;
	private Long subExpenseId;

}
