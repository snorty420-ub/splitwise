package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
