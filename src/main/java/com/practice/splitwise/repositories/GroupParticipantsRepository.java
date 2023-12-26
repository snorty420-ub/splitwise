package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Friendship;
import com.practice.splitwise.data.GroupParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupParticipantsRepository extends JpaRepository<GroupParticipants, Long> {
}
