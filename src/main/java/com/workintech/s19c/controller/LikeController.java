package com.workintech.s19c.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.workintech.s19c.service.LikeService;
import com.workintech.s19c.dto.LikeDto;


@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseEntity<LikeDto> likeTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        LikeDto newLike = likeService.likeTweet(userId, tweetId);
        return ResponseEntity.ok(newLike);
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislikeTweet(@RequestParam Long userId, @RequestParam Long tweetId) {
        likeService.dislikeTweet(userId, tweetId);
        return ResponseEntity.noContent().build();
    }


}

