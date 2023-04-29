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
		Message message = messageService.findById(messageRoom.getMessageRoomId());
		return new MessageRoomResponseDto(messageRoom);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public MessageRoomListResponseDto getMessageRoomList(@RequestParam final Long memberId)
	{
		List<MessageRoom> messageRoomList = messageRoomService.findByOwner(memberId);
		return MessageRoomListResponseDto.of(messageRoomList);
	}
	@GetMapping("/{messageRoomId}")
	@ResponseStatus(value = HttpStatus.OK)
	public MessageListResponseDto getMessageList(@PathVariable final Long messageRoomId, @RequestParam final Long memberId){
		Member currentMember = memberService.findById(memberId);
		MessageRoom messageRoom = messageRoomService.findById(messageRoomId);
		List<Message> messageList = messageRoomService.readMessages(messageRoom,currentMember);
		return MessageListResponseDto.of(messageList, messageRoom, currentMember);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public MessageRoomCheckResponseDto checkMessageRoom(@RequestParam final Long senderId, @RequestParam final Long receiverId, @RequestParam final Long createdFrom)
	{
		Long id = messageRoomService.checkMessageRoom(senderId, receiverId, createdFrom);
		return new MessageRoomCheckResponseDto(id);
	}

	@DeleteMapping("/{messageRoomId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteMessageRoom(@PathVariable final Long messageRoomId, @RequestParam final Long memberId)
	{
		messageRoomService.delete(messageRoomId, memberId);
		return "성공적으로 삭제되었습니다.";
	}




}
