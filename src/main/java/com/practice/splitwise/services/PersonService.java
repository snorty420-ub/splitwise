package com.practice.splitwise.services;

import com.practice.splitwise.data.Friendship;
import com.practice.splitwise.data.Person;
import com.practice.splitwise.dtos.requests.UpdatePerson;
import com.practice.splitwise.exceptions.PersonNotFoundException;
import com.practice.splitwise.repositories.FriendshipRepository;
import com.practice.splitwise.repositories.PersonRepository;
import com.practice.splitwise.utilities.Utilities;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {

	private final PersonRepository personRepository;
	private final FriendshipRepository friendshipRepository;
//	@Autowired
	public PersonService(PersonRepository personRepository, FriendshipRepository friendshipRepository){
		this.personRepository = personRepository;
		this.friendshipRepository = friendshipRepository;
	}


	public List<Person> getAllPersons(){
		return Utilities.IterableToList(personRepository.findAll());
	}

	public Person getPersonById(Long id){
		return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
	}

	public Long insertPerson(Person person){
		return personRepository.save(person).getId();
	}

	public Person updatePerson(Long id, UpdatePerson updatedPerson) {
		Optional<Person> personOptional = personRepository.findById(id);
		if(!personOptional.isPresent()){
			throw new PersonNotFoundException();
		}
		Person person = personOptional.get();
		person.setName(updatedPerson.getName());
		person.setPassword(updatedPerson.getPassword());
		return personRepository.save(person);
	}

	public void deletePerson(Long id) {
		personRepository.deleteById(id);
	}


	public Long establishFriendship(Long person1, Long person2) {
		// we have to add locks as this update can be removed by other thread that is updating the other details of the person
		Optional<Person> person1Optional = personRepository.findById(person1);
		Optional<Person> person2Optional = personRepository.findById(person2);
		if(!person1Optional.isPresent() || !person2Optional.isPresent()){
			throw new PersonNotFoundException();
		}
        Friendship save = friendshipRepository.save(Friendship.builder().self(person1).friend(person2).amount(0).build());
//        friendshipRepository.save(Friendship.builder().self(person2).friend(person1).amount(0).build())
		// we have to handle a case if the friendship already exists
		// we have to handle a case if one addition failed but another succeeded
		return save.getId();
	}
}
