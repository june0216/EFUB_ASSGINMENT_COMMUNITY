package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.*;
import com.efub.community.domain.chat.service.MessageRoomService;
import com.efub.community.domain.chat.service.MessageService;
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
@RequestMapping("/messagerooms")
@RequiredArgsConstructor
public class MessageRoomController {
	private final MessageRoomService messageRoomService;
	private final MessageService messageService;
	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public MessageRoomResponseDto createMessageRoom(@RequestBody @Valid final MessageRoomRequestDto requestDto)
	{
		Long id = messageRoomService.createMessageRoom(requestDto);
		MessageRoom messageRoom = messageRoomService.findById(id);
		return new MessageRoomResponseDto(messageRoom);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public MessageRoomListResponseDto getMessageRoomList(@RequestParam final Long memberId)
	{
		List<MessageRoom> messageRoomList = messageRoomService.findByOwner(memberId);
		return MessageRoomListResponseDto.of(messageRoomList);
	}

	@GetMapping("/check")
	@ResponseStatus(value = HttpStatus.OK)//같은 글에서 초기 sender가 receiver이고 초기 receiver가 sender가 된다면 다른 메시지방 생성 가능
	public String checkMessageRoom(@RequestParam final Long senderId, @RequestParam final Long receiverId, @RequestParam final Long createdFrom)
	{
		String isExisted = messageRoomService.checkMessageRoom(senderId, receiverId, createdFrom);
		return isExisted;
	}

	@DeleteMapping("/{messageRoomId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteMessageRoom(@PathVariable final Long messageRoomId, @RequestParam final Long memberId)
	{
		messageRoomService.delete(messageRoomId, memberId);
		return "성공적으로 삭제되었습니다.";
	}




}
