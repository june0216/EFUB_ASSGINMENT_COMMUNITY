package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRoomListResponseDto;
import com.efub.community.domain.chat.dto.MessageRoomRequestDto;
import com.efub.community.domain.chat.dto.MessageRoomResponseDto;
import com.efub.community.domain.chat.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
