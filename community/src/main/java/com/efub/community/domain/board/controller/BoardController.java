package com.efub.community.domain.board.controller;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.*;
import com.efub.community.domain.board.service.BoardService;
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
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;


	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public BoardListResponseDto.SingleBoard createBoard(@RequestBody @Valid final BoardRequestDto requestDto) {
		Long id = boardService.create(requestDto);
		Board board = boardService.findById(id);
		return BoardListResponseDto.SingleBoard.of(board);
	}

	@GetMapping("/{boardId}")
	@ResponseStatus(value = HttpStatus.OK)
	public BoardListResponseDto.SingleBoard readBoard(@PathVariable final Long boardId) {
		Board board = boardService.findById(boardId);
		return BoardListResponseDto.SingleBoard.of(board);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)// 전체 조회
	public BoardListResponseDto readBoardList() {
		List<Board> boardList = boardService.findAllDesc();
		return BoardListResponseDto.of(boardList);
	}



	@PutMapping("/{boardId}")
	@ResponseStatus(value = HttpStatus.OK)
	public BoardListResponseDto.SingleBoard updateBoard(@PathVariable final Long boardId, @RequestBody final BoardRequestDto requestDto) {
		boardService.update(boardId, requestDto);
		Board board = boardService.findById(boardId);
		return BoardListResponseDto.SingleBoard.of(board);
	}

	@DeleteMapping("/{boardId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteBoard(@PathVariable final Long boardId, @RequestParam final Long memberId) {
		boardService.delete(boardId, memberId);
		return "성공적으로 삭제되었습니다.";
	}
}
