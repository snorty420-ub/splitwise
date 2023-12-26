package com.practice.splitwise.repositories;

import com.practice.splitwise.data.GroupParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupParticipantsRepository extends JpaRepository<GroupParticipants, Long> {

    Optional<List<Long>> findByGroupId(Long groupId);
}
