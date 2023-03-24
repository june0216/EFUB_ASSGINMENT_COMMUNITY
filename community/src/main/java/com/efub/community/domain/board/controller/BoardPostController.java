package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.PostListResponseDto;
import com.efub.community.domain.board.dto.PostRequestDto;
import com.efub.community.domain.board.dto.PostResponseDto;
import com.efub.community.domain.board.service.BoardService;
import com.efub.community.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardPostController {
	private final PostService postService;

	@GetMapping("/{boardId}/posts")
	public PostListResponseDto readPostList(@PathVariable final Long boardId) {
		List<Post> postList = postService.findPostsByBoard(boardId);
		return PostListResponseDto.of(postList);
	}

	@PostMapping("/{boardId}/posts")
	public PostResponseDto createPost(@PathVariable final Long boardId, @RequestBody @Valid final PostRequestDto requestDto) {
		Long id = postService.create(boardId, requestDto);
		Post post = postService.findById(id);
		return PostResponseDto.of(post);
	}
}
