package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("select f from Friendship f where\n" +
            "  (f.friend = :friend and f.self = :self) or\n" +
            "  (f.self = :friend and f.friend = :self)\n")
    Optional<Friendship> getFriendshipBySelfAndFriend(Long self, Long friend);
}