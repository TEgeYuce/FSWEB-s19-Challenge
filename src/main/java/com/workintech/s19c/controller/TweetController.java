package com.workintech.s19c.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.workintech.s19c.service.TweetService;
import com.workintech.s19c.entity.Tweet;
import com.workintech.s19c.dto.TweetUpdateDto;
import com.workintech.s19c.dto.TweetDto;


@RestController
@RequestMapping("/tweet")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet tweet, @RequestParam Long userId) {
        TweetDto newTweetDto = tweetService.createTweet(userId, tweet);

        return ResponseEntity.ok(newTweetDto);
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<List<TweetDto>> getTweetsByUserId(@RequestParam Long userId) {
        List<TweetDto> tweets = tweetService.getTweetsByUserId(userId);

        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/findById")
    public ResponseEntity<TweetDto> getTweetById(@RequestParam Long tweetId) {

        return tweetService.getTweetById(tweetId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable("id") Long tweetId, @RequestBody TweetUpdateDto updatedTweetDto) {
        TweetDto tweet = tweetService.updateTweet(tweetId, updatedTweetDto.getContent());

        return ResponseEntity.ok(tweet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId) {
        tweetService.deleteTweet(tweetId);

        return ResponseEntity.noContent().build();

    }


}

