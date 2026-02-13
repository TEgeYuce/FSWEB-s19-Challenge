package com.workintech.s19c.service;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.workintech.s19c.entity.User;
import com.workintech.s19c.entity.Tweet;
import com.workintech.s19c.entity.Retweet;
import com.workintech.s19c.repository.UserRepository;
import com.workintech.s19c.repository.TweetRepository;
import com.workintech.s19c.repository.RetweetRepository;
import com.workintech.s19c.dto.RetweetDto;


@Service
public class RetweetService {
    private final RetweetRepository retweetRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public RetweetService(RetweetRepository retweetRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public RetweetDto retweetTweet(Long userId, Long tweetId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isEmpty()) {

            throw new RuntimeException("User don't found with this id!! -> " + userId);
        }

        if (tweetOptional.isEmpty()) {

            throw new RuntimeException("Tweet don't found with this id!! -> " + tweetId);
        }

        Optional<Retweet> existingRetweet = retweetRepository.findByUserIdAndTweetId(userId, tweetId);
        if (existingRetweet.isPresent()) {

            throw new IllegalStateException("User has already retweeted for this tweet!! ");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(userOptional.get());
        retweet.setTweet(tweetOptional.get());
        Retweet savedRetweet = retweetRepository.save(retweet);

        return convertToDto(savedRetweet);
    }

    public void deleteRetweet(Long retweetId) {
        retweetRepository.deleteById(retweetId);
    }

    private RetweetDto convertToDto(Retweet retweet) {

        RetweetDto retweetDto = new RetweetDto();
        retweetDto.setId(retweet.getId());
        retweetDto.setUserId(retweet.getUser().getId());
        retweetDto.setTweetId(retweet.getTweet().getId());

        return retweetDto;

    }


}

