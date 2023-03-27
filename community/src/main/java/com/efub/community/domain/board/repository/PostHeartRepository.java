package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.domain.PostHeart;
import com.efub.community.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
	Integer countByPost(Post post);
	List<PostHeart> findByWriter(Member memebr);
	boolean existsByWriterAndPost(Member memebr, Post post);
	Optional<PostHeart> findByWriterAndPost(Member memebr, Post post);

}
