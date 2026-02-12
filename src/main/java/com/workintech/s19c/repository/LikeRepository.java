package com.workintech.s19c.repository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.s19c.entity.Like;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);


}

