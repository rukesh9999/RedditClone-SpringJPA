package com.ReditClone.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReditClone.model.Post;
import com.ReditClone.model.Subreddit;
import com.ReditClone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


		List<Post> findAllBySubreddit(Subreddit subReddit);

		List<Post> findByUser(User user);


	
	
}
