package com.practice.splitwise.repositories;

import com.practice.splitwise.data.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship getFriendshipBySelfAndFriend(Long first, Long second);
}