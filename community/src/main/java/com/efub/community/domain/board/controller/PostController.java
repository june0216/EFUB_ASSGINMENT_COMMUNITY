package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.request.MemberInfoRequestDto;
import com.efub.community.domain.board.dto.request.PostRequestDto;
import com.efub.community.domain.board.dto.response.PostListResponseDto;
import com.efub.community.domain.board.dto.response.PostResponseDto;
import com.efub.community.domain.board.service.PostHeartService;
import com.efub.community.domain.board.service.PostService;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final PostHeartService postHeartService;
	private final MemberService memberService;



	@GetMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)// 글 1개 조회
	public PostResponseDto readPost(@PathVariable final Long postId, @RequestParam final Long memberId) {
		Post post = postService.findById(postId);
		Integer heartCount = postHeartService.countPostHeart(post);
		boolean isHeart = postHeartService.isHeart(memberId, post);
		PostResponseDto responseDto = PostResponseDto.of(post);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}
	@GetMapping("/hot")
	@ResponseStatus(value = HttpStatus.OK)
	public PostListResponseDto getHotPostList() {
		List<Post> postList = postService.getHotList();
		return PostListResponseDto.of(postList);
	}

	@PutMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public PostResponseDto updatePost(@PathVariable final Long postId, @RequestBody final PostRequestDto requestDto) {
		postService.update(postId, requestDto);
		Post post = postService.findById(postId);
		Member member = memberService.findById(requestDto.getMemberId());
		Integer heartCount = postHeartService.countPostHeart(post);
		boolean isHeart = postHeartService.isExistsByWriterAndPost(member, post);
		PostResponseDto responseDto = PostResponseDto.of(post);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}

	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(@PathVariable final Long postId,  @RequestParam final Long memberId) {
		postService.delete(postId, memberId);
		return "성공적으로 삭제되었습니다.";
	}

	@PostMapping("/{postId}/hearts")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createPostLike(@PathVariable final Long postId, @RequestBody final MemberInfoRequestDto requestDto) {
		postHeartService.create(postId, requestDto);
		return "좋아요를 눌렀습니다.";
	}

	@DeleteMapping("/{postId}/hearts")
	@ResponseStatus(value = HttpStatus.OK)
	public String deletePostLike(@PathVariable final Long postId ,@RequestParam final Long memberId) {
		postHeartService.delete(postId, memberId);
		return "좋아요가 취소되었습니다.";
	}

}
