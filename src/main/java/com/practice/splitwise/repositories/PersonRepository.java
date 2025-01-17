package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}