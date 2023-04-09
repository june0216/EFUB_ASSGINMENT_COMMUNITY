package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.dto.request.CommentRequestDto;
import com.efub.community.domain.board.dto.response.CommentResponseDto;
import com.efub.community.domain.board.service.CommentService;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	private final MemberService memberService;

	@GetMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public CommentResponseDto readComment(@PathVariable final Long commentId, @RequestParam final Long memberId) {
		Comment comment = commentService.findById(commentId);
		Member member = memberService.findById(memberId);

		CommentResponseDto responseDto = CommentResponseDto.of(comment);
		return responseDto;
	}

	@PutMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public CommentResponseDto updateComment(@PathVariable final Long commentId, @RequestBody @Valid final CommentRequestDto requestDto) {
		commentService.update(requestDto, commentId);
		Comment comment = commentService.findById(commentId);
		Member member = memberService.findById(requestDto.getMemberId());

		CommentResponseDto responseDto = CommentResponseDto.of(comment);
		return responseDto;
	}

	@DeleteMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteComment(@PathVariable final Long commentId, @RequestParam final Long memberId) {
		commentService.delete(commentId, memberId);
		return "성공적으로 삭제되었습니다.";
	}
}
