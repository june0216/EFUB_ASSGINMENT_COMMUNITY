package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Override
	Optional<Post> findById(Long postId);

	List<Post> findByWriter(Member member);
	List<Post> findAllByOrderByPostIdDesc();

	List<Post> findAllByBoard(Board board);

	@Query("SELECT p FROM Post p WHERE (SELECT COUNT(l) FROM p.postHeartList l) >= :heartCount ORDER BY p.createdDate DESC")
	List<Post> findPopularPosts(@Param("heartCount") Integer heartCount);
}
