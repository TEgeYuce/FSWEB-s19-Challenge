package com.workintech.s19c.repository;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.s19c.entity.Tweet;


@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUserId(Long userId);

}

