package com.workintech.s19c.service;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.workintech.s19c.entity.User;
import com.workintech.s19c.entity.Tweet;
import com.workintech.s19c.repository.UserRepository;
import com.workintech.s19c.repository.TweetRepository;
import com.workintech.s19c.dto.UserDto;
import com.workintech.s19c.dto.TweetDto;


@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public TweetDto createTweet(Long userId, Tweet tweet) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {

            throw new RuntimeException("User not found with this id!! -> " + userId);
        }

        User user = userOptional.get();
        tweet.setUser(user);
        tweet.setCreationDate(LocalDateTime.now());
        Tweet savedTweet = tweetRepository.save(tweet);

        return convertToDto(savedTweet);
    }

    public List<TweetDto> getAllTweets() {

        return tweetRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TweetDto> getTweetsByUserId(Long userId) {

        return tweetRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<TweetDto> getTweetById(Long tweetId) {

        return tweetRepository.findById(tweetId)
                .map(this::convertToDto);
    }

    public TweetDto updateTweet(Long tweetId, String newContent) {

        Tweet existingTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found with this id!! -> " + tweetId));
        existingTweet.setContent(newContent);
        Tweet updatedTweet = tweetRepository.save(existingTweet);

        return convertToDto(updatedTweet);
    }

    public void deleteTweet(Long tweetId) {
        tweetRepository.deleteById(tweetId);
    }

    private TweetDto convertToDto(Tweet tweet) {

        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setContent(tweet.getContent());
        tweetDto.setCreationDate(tweet.getCreationDate());

        UserDto userDto = new UserDto();
        userDto.setId(tweet.getUser().getId());
        userDto.setUsername(tweet.getUser().getUsername());
        tweetDto.setUser(userDto);

        return tweetDto;

    }


}

