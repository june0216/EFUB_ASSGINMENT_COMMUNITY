package com.efub.community.domain.board.service;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.request.PostRequestDto;
import com.efub.community.domain.board.repository.PostRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class PostService {
	private final PostRepository postRepository;
	private final MemberService memberService; //서비스 안에서 다른 서비스 호출 가능
	private final BoardService boardService;


	public Long create(Long boardId, PostRequestDto requestDto) {
		Member member = memberService.findById(requestDto.getMemberId());
		Board board = boardService.findById(boardId);
		Post post = postRepository.save(requestDto.toEntity(member, board));
		return post.getPostId();
	}


	public void update(Long postId, PostRequestDto requestDto) {
		Post post = findById(postId);
		checkValidMember(requestDto.getMemberId(), post.getWriter().getMemberId());
		post.updatePost(requestDto.getContent());
	}

	public void delete(Long postId, Long memberId) {
		Post post = findById(postId);
		checkValidMember(memberId, post.getWriter().getMemberId());
		postRepository.delete(post);
	}

	private void checkValidMember(Long currentMemberId, Long tagetMemberId){
		if(currentMemberId != tagetMemberId)
		{
			throw new IllegalArgumentException();
		}
	}

	@Transactional(readOnly = true)
	public Post findById(Long postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));
	}
	@Transactional(readOnly = true)
	public List<Post> findAllDesc() {
		return postRepository.findAllByOrderByPostIdDesc();
	}


	@Transactional(readOnly = true)
	public List<Post> findByWriter(Long accountId) {
		Member writer = memberService.findById(accountId);
		return postRepository.findByWriter(writer);
	}
	@Transactional(readOnly = true)
	public List<Post> findPostsByBoard(Long boardId)
	{
		Board board = boardService.findById(boardId);
		return postRepository.findAllByBoard(board);
	}



}
