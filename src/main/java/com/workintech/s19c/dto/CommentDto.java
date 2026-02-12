package com.workintech.s19c.dto;
import java.time.LocalDateTime;
import lombok.Data;


@Data
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime creationDate;
    private UserDto user;
    private Long tweetId;

}

