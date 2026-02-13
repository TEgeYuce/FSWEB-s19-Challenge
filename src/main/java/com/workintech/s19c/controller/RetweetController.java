package com.workintech.s19c.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.workintech.s19c.service.RetweetService;
import com.workintech.s19c.dto.RetweetDto;


@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public ResponseEntity<RetweetDto> retweetTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        RetweetDto newRetweet = retweetService.retweetTweet(userId, tweetId);
        return ResponseEntity.ok(newRetweet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetweet(@PathVariable("id") Long retweetId) {
        retweetService.deleteRetweet(retweetId);

        return ResponseEntity.noContent().build();

    }


}

