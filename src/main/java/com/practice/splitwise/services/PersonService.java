package com.practice.splitwise.services;

import com.practice.splitwise.beans.Friendship;
import com.practice.splitwise.beans.Person;
import com.practice.splitwise.dtos.requests.UpdatePerson;
import com.practice.splitwise.exceptions.PersonNotFoundException;
import com.practice.splitwise.repositories.PersonRepository;
import com.practice.splitwise.utilities.Utilities;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository){
		this.personRepository = personRepository;
	}

	public List<Person> getAllPersons(){
		return Utilities.IterableToList(personRepository.findAll());
	}

	public Person getPersonById(UUID id){
		return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
	}

	public UUID insertPerson(Person person){
		return personRepository.save(person).getId();
	}

	public Person updatePerson(UUID id, UpdatePerson updatedPerson) {
		Optional<Person> personOptional = personRepository.findById(id);
		if(!personOptional.isPresent()){
			throw new PersonNotFoundException();
		}
		Person person = personOptional.get();
		person.setName(updatedPerson.getName());
		person.setPassword(updatedPerson.getPassword());
		return personRepository.save(person);
	}

	public void deletePerson(UUID id) {
		personRepository.deleteById(id);
	}


	public UUID establishFriendship(UUID person1, UUID person2) {
		// we have to add locks as this update can be removed by other thread that is updating the other details of the person
		Optional<Person> person1Optional = personRepository.findById(person1);
		Optional<Person> person2Optional = personRepository.findById(person2);
		if(!person1Optional.isPresent() || !person2Optional.isPresent()){
			throw new PersonNotFoundException();
		}
		Person person1Obj = person1Optional.get();
		Person person2Obj = person2Optional.get();
		person1Obj.getFriendsList().add(new Friendship(person2Obj,0));
		person2Obj.getFriendsList().add(new Friendship(person1Obj,0));
		// we have to handle a case if the friendship already exists
		// we have to handle a case if one addition failed but another succeeded
		personRepository.save(person1Obj);
		personRepository.save(person2Obj);
		return person1;
	}
}
