package com.workintech.s19c.service;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.workintech.s19c.entity.User;
import com.workintech.s19c.entity.Tweet;
import com.workintech.s19c.entity.Like;
import com.workintech.s19c.repository.UserRepository;
import com.workintech.s19c.repository.TweetRepository;
import com.workintech.s19c.repository.LikeRepository;
import com.workintech.s19c.dto.LikeDto;


@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public LikeDto likeTweet(Long userId, Long tweetId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isEmpty()) {

            throw new RuntimeException("User don't found with this id!! -> " + userId);
        }

        if (tweetOptional.isEmpty()) {

            throw new RuntimeException("Tweet don't found with this id!! -> " + tweetId);
        }

        Optional<Like> existingLike = likeRepository.findByUserIdAndTweetId(userId, tweetId);
        if (existingLike.isPresent()) {

            throw new IllegalStateException("User has already liked for this tweet!! ");
        }

        Like like = new Like();
        like.setUser(userOptional.get());
        like.setTweet(tweetOptional.get());
        Like savedLike = likeRepository.save(like);

        return convertToDto(savedLike);
    }

    public void dislikeTweet(Long userId, Long tweetId) {
        Optional<Like> existingLike = likeRepository.findByUserIdAndTweetId(userId, tweetId);
        if (existingLike.isEmpty()) {

            throw new RuntimeException("Like don't found for this tweet and user!! ");
        }
        likeRepository.delete(existingLike.get());
    }

    private LikeDto convertToDto(Like like) {

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setUserId(like.getUser().getId());
        likeDto.setTweetId(like.getTweet().getId());

        return likeDto;

    }


}

