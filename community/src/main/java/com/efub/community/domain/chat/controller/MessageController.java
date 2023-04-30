package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.*;
import com.efub.community.domain.chat.service.MessageRoomService;
import com.efub.community.domain.chat.service.MessageService;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/messagerooms/{messageRoomId}/messages")
@RequiredArgsConstructor
public class MessageController {
	private final MessageService messageService;
	private final MemberService memberService;
	private final MessageRoomService messageRoomService;
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public MessageResponseDto createMessage(@PathVariable final Long messageRoomId , @RequestBody @Valid final MessageRequestDto requestDto)
	{
		Long id = messageService.createMessage(messageRoomId, requestDto);
		Message message = messageService.findById(id);
		return new MessageResponseDto(message);
	}
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public MessageListResponseDto getMessageList(@PathVariable final Long messageRoomId, @RequestParam final Long memberId){
		Member currentMember = memberService.findById(memberId);
		MessageRoom messageRoom = messageRoomService.findById(messageRoomId);
		List<Message> messageList = messageService.readMessages(messageRoom,currentMember);
		return MessageListResponseDto.of(messageList, messageRoom, currentMember);
	}



}
