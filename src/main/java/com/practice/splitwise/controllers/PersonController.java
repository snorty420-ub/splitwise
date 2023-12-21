package com.practice.splitwise.controllers;

import com.practice.splitwise.dtos.requests.AddFriendship;
import com.practice.splitwise.dtos.requests.UpdatePerson;
import com.practice.splitwise.services.PersonService;
import com.practice.splitwise.data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
	public Person getPersonById(@PathVariable("id") Long id){
		return personService.getPersonById(id);
	}

	@PostMapping
	public Long insertPerson(@RequestBody Person person){
		return personService.insertPerson(person);
	}

	@PostMapping("/friend/add")
	public Long addFriend(@RequestBody AddFriendship friendship)	{
		return personService.establishFriendship(friendship.getPerson1(), friendship.getPerson2());
	}

	@PutMapping("/{id}")
	public Person updatePerson(@PathVariable Long id, @RequestBody UpdatePerson person){
		return personService.updatePerson(id,person);
	}

	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable Long id){
		personService.deletePerson(id);
	}

}
