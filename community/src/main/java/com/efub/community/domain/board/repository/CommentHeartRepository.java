package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.CommentHeart;
import com.efub.community.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
	Integer countByComment(Comment comment);
	List<CommentHeart> findByWriter(Member member);
	boolean existsByWriterAndComment(Member member, Comment comment);
	Optional<CommentHeart> findByWriterAndComment(Member member, Comment comment);
}
