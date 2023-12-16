package com.practice.splitwise.beans;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;
	private String name;
	private String userName;
	private String password;
	private List<Friendship> friendsList;

}
