package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Spender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpenderRepository extends JpaRepository<Spender, Long> {

}
