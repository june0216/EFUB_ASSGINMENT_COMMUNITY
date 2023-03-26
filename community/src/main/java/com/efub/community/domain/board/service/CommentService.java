package com.efub.community.domain.board.service;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.CommentRequestDto;
import com.efub.community.domain.board.repository.CommentRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	private final PostService postService;
	private final MemberService memberService;


	public Long create(CommentRequestDto requestDto, Long postId) {
		Member member = memberService.findById(requestDto.getMemberId());
		Post post = postService.findById(postId);
		Comment comment = commentRepository.save(requestDto.toEntity(post, member));
		return comment.getCommentId();
	}

	public void update(CommentRequestDto requestDto, Long commentId) {
		Comment comment = findById(commentId);
		comment.updateComment(requestDto.getContent());
	}

	public void delete(Long commentId) {
		Comment comment = findById(commentId);
		commentRepository.delete(comment);
	}

	// 연관관계 편의 메소드를 사용한 코드
	@Transactional(readOnly = true)
	public List<Comment> findByPostObj(Long postId) {
		Post post = postService.findById(postId);
		List<Comment> commentList = post.getCommentList();
		log.info("post.getCommentList = {}", commentList);
		return commentList;
	}


	@Transactional(readOnly = true)
	public Comment findById(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commentId));
	}

	@Transactional(readOnly = true)
	public List<Comment> findByWriter(Member author) {
		return commentRepository.findByWriter(author);
	}

	@Transactional(readOnly = true)
	public List<Comment> findByPost(Long postId) {
		Post post = postService.findById(postId);
		return commentRepository.findByPost(post);
	}
}