package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.MemberInfoRequestDto;
import com.efub.community.domain.board.dto.PostListResponseDto;
import com.efub.community.domain.board.dto.PostRequestDto;
import com.efub.community.domain.board.dto.PostResponseDto;
import com.efub.community.domain.board.service.PostService;
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



	@GetMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)// 글 1개 조회
	public PostResponseDto readPost(@PathVariable Long postId) {
		Post post = postService.findById(postId);
		return PostResponseDto.of(post);
	}

	@PutMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public PostResponseDto updatePost(@PathVariable final Long postId, @RequestBody final PostRequestDto requestDto) {
		postService.update(postId, requestDto);
		Post post = postService.findById(postId);
		return PostResponseDto.of(post);
	}

	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deletePost(@PathVariable final Long postId, @RequestBody @Valid MemberInfoRequestDto requestDto) {
		postService.delete(postId, requestDto);
		return "성공적으로 삭제되었습니다.";
	}

}
