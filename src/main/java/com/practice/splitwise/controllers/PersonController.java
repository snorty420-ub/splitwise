package com.practice.splitwise.controllers;

import com.practice.splitwise.beans.Expense;
import com.practice.splitwise.dtos.requests.UpdatePerson;
import com.practice.splitwise.services.ExpenseService;
import com.practice.splitwise.services.PersonService;
import com.practice.splitwise.beans.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@GetMapping
	public List<Person> getPersons(){
		return personService.getAllPersons();
	}

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable("id") UUID id){
		return personService.getPersonById(id);
	}

	@PostMapping
	public UUID insertPerson(@RequestBody Person person){
		return personService.insertPerson(person);
	}

	@PostMapping
	public UUID addFriend(@RequestBody UUID person1, @RequestBody UUID person2)	{
		return personService.establishFriendship(person1, person2);
	}

	@PutMapping("/{id}")
	public Person updatePerson(@PathVariable UUID id, @RequestBody UpdatePerson person){
		return personService.updatePerson(id,person);
	}

	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable UUID id){
		personService.deletePerson(id);
	}

}
