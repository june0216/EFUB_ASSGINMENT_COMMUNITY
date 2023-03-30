package com.efub.community.domain.board.service;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.domain.PostHeart;
import com.efub.community.domain.board.dto.request.MemberInfoRequestDto;
import com.efub.community.domain.board.repository.PostHeartRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {
	private final PostHeartRepository postHeartRepository;
	private final PostService postService;
	private final MemberService memberService;

	public void create(Long postId, MemberInfoRequestDto requestDto) {
		Post post = postService.findById(postId);
		Member member = memberService.findById(requestDto.getMemberId());
		if (isExistsByWriterAndPost(member, post)) {
			throw new RuntimeException("이미 좋아요를 눌렀습니다.");
		}
		PostHeart postHeart = PostHeart.builder()
				.post(post)
				.member(member)
				.build();
		postHeartRepository.save(postHeart);
	}
	public void delete(Long postId, Long memberId) {
		Post post = postService.findById(postId);
		Member member = memberService.findById(memberId);
		PostHeart postHeart = postHeartRepository.findByWriterAndPost(member, post)
				.orElseThrow(() -> new IllegalArgumentException("좋아요가 존재하지 않습니다."));
		postHeartRepository.delete(postHeart);
	}

	public boolean isHeart(Long accountId, Post post){
		Member member = memberService.findById(accountId);
		return isExistsByWriterAndPost(member, post);
	}

	@Transactional(readOnly = true)
	public boolean isExistsByWriterAndPost(Member member, Post post) {
		return postHeartRepository.existsByWriterAndPost(member, post);
	}

	@Transactional(readOnly = true)
	public PostHeart findById(Long postHeartId) {
		return postHeartRepository.findById(postHeartId)
				.orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다. id=" + postHeartId));
	}


	@Transactional(readOnly = true)
	public Integer countPostHeart(Post post) {
		Integer count = postHeartRepository.countByPost(post);
		return count;
	}

	@Transactional(readOnly = true)
	public List<PostHeart> findByWriter(Member member) {
		return postHeartRepository.findByWriter(member);
	}

	@Transactional(readOnly = true)
	public List<Post> findLikePostList(List<PostHeart> postLikeList) {
		return postLikeList.stream()
				.map(PostHeart::getPost)
				.collect(Collectors.toList());
	}
}
