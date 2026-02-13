package com.workintech.s19c.service;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.workintech.s19c.entity.User;
import com.workintech.s19c.entity.Tweet;
import com.workintech.s19c.entity.Comment;
import com.workintech.s19c.repository.UserRepository;
import com.workintech.s19c.repository.TweetRepository;
import com.workintech.s19c.repository.CommentRepository;
import com.workintech.s19c.dto.UserDto;
import com.workintech.s19c.dto.CommentDto;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, TweetRepository tweetRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public CommentDto createComment(Long userId, Long tweetId, Comment comment) {

        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Tweet> tweetOptional = tweetRepository.findById(tweetId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with this id!! -> " + userId);
        }

        if (tweetOptional.isEmpty()) {
            throw new RuntimeException("Tweet not found with this id!! -> " + tweetId);
        }

        comment.setUser(userOptional.get());
        comment.setTweet(tweetOptional.get());
        comment.setCreationDate(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        return convertToDto(savedComment);
    }

    public CommentDto updateComment(Long commentId, String newContent) {

        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with this id!! -> " + commentId));
        existingComment.setContent(newContent);
        Comment updatedComment = commentRepository.save(existingComment);

        return convertToDto(updatedComment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private CommentDto convertToDto(Comment comment) {

        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreationDate(comment.getCreationDate());
        commentDto.setTweetId(comment.getTweet().getId());

        UserDto userDto = new UserDto();
        userDto.setId(comment.getUser().getId());
        userDto.setUsername(comment.getUser().getUsername());
        commentDto.setUser(userDto);
        return commentDto;

    }


}

