package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
