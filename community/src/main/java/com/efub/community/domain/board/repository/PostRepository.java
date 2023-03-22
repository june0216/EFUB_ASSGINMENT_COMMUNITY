package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Override
	Optional<Post> findById(Long postId);

	List<Post> findByWriter(Member member);
	List<Post> findAllByOrderByPostIdDesc();

	List<Post> findAllByBoard(Board board);
}
