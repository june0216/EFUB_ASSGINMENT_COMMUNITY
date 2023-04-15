package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRoomCheckResponseDto;
import com.efub.community.domain.chat.dto.MessageRoomListResponseDto;
import com.efub.community.domain.chat.dto.MessageRoomRequestDto;
import com.efub.community.domain.chat.dto.MessageRoomResponseDto;
import com.efub.community.domain.chat.service.MessageRoomService;
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

	///messageRooms?senderId={member_id}?receiverId={member_id}?createdFrom={post_id}
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
