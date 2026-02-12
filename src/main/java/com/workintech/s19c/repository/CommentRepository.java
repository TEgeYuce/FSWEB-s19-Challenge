package com.workintech.s19c.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.workintech.s19c.entity.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}

