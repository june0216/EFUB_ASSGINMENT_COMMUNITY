package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.response.CommentListResponseDto;
import com.efub.community.domain.board.dto.response.PostListResponseDto;
import com.efub.community.domain.board.service.CommentService;
import com.efub.community.domain.board.service.PostService;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members/{memberId}")
@RequiredArgsConstructor
public class MemberBoardController {
	private final PostService postService;

	@GetMapping("/posts")//작성자 별 글 조회
	@ResponseStatus(value = HttpStatus.OK)
	public PostListResponseDto readPostList(@PathVariable final Long accountId) {
		List<Post> postList = postService.findByWriter(accountId);
		return PostListResponseDto.of(postList);
	}



}
