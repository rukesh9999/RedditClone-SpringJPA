package com.ReditClone.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReditClone.model.Post;
import com.ReditClone.model.User;
import com.ReditClone.model.Vote;


@Repository
public interface VoteRepository  extends JpaRepository<Vote, Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
