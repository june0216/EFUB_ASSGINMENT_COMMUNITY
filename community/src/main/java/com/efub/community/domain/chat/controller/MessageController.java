package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRequestDto;
import com.efub.community.domain.chat.dto.MessageResponseDto;
import com.efub.community.domain.chat.dto.MessageRoomRequestDto;
import com.efub.community.domain.chat.dto.MessageRoomResponseDto;
import com.efub.community.domain.chat.service.MessageService;
import com.efub.community.domain.member.domain.Member;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/messagerooms/{messageRoomId}/messages")
@RequiredArgsConstructor
public class MessageController {
	final private MessageService messageService;
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public MessageResponseDto createMessage(@PathVariable final Long messageRoomId , @RequestBody @Valid final MessageRequestDto requestDto)
	{
		Long id = messageService.createMessage(requestDto);
		Message message = messageService.findById(id);
		return new MessageResponseDto(message);
	}

}
