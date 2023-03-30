package com.efub.community.domain.board.service;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.CommentHeart;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.domain.PostHeart;
import com.efub.community.domain.board.dto.request.MemberInfoRequestDto;
import com.efub.community.domain.board.repository.CommentHeartRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentHeartService {
	private final CommentService commentService;
	private final CommentHeartRepository commentHeartRepository;


	private final MemberService memberService;

	public void create(Long commentId, MemberInfoRequestDto requestDto) {
		Member member = memberService.findById(requestDto.getMemberId());
		Comment comment = commentService.findById(commentId);
		if (isExistsByWriterAndComment(member, comment)) {
			throw new RuntimeException("이미 좋아요를 눌렀습니다.");
		}
		CommentHeart commentHeart = CommentHeart.builder()
				.comment(comment)
				.member(member)
				.build();
		commentHeartRepository.save(commentHeart);
	}

	public void delete(Long commentId, Long memberId) {
		Member member = memberService.findById(memberId);
		Comment comment = commentService.findById(commentId);
		CommentHeart commentHeart = commentHeartRepository.findByWriterAndComment(member, comment)
				.orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다."));
		commentHeartRepository.delete(commentHeart);
	}

	public boolean isHeart(Long accountId, Comment comment){
		Member member = memberService.findById(accountId);
		return isExistsByWriterAndComment(member, comment);
	}

	@Transactional(readOnly = true)
	public CommentHeart findById(Long commentHeartId) {
		return commentHeartRepository.findById(commentHeartId)
				.orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다. id=" + commentHeartId));
	}
	@Transactional(readOnly = true)
	public boolean isExistsByWriterAndComment(Member member, Comment comment) {
		return commentHeartRepository.existsByWriterAndComment(member, comment);
	}



	@Transactional(readOnly = true)
	public Integer countCommentHeart(Comment comment) {
		Integer count = commentHeartRepository.countByComment(comment);
		return count;
	}

	@Transactional(readOnly = true)
	public List<CommentHeart> findByWriter(Member member) {
		return commentHeartRepository.findByWriter(member);
	}

	@Transactional(readOnly = true)
	public List<Post> findLikePostList(List<PostHeart> postLikeList) {
		return postLikeList.stream()
				.map(PostHeart::getPost)
				.collect(Collectors.toList());
	}
}
