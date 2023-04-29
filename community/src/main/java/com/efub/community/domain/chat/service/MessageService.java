package com.efub.community.domain.chat.service;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.service.PostService;
import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRequestDto;
import com.efub.community.domain.chat.dto.MessageRoomRequestDto;
import com.efub.community.domain.chat.repository.MessageRepository;
import com.efub.community.domain.chat.repository.MessageRoomRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class MessageService {
	private final MemberService memberService;
	private final MessageRepository messageRepository;
	private final MessageRoomService messageRoomService;
	private final MessageRoomRepository messageRoomRepository;
	public Long createMessage(MessageRequestDto request) {
		Member sender = memberService.findById(request.getSenderId());
		MessageRoom messageRoom = messageRoomService.findById(request.getMessageRoomId());
		checkValidOwner(request.getSenderId(), messageRoom);
		Message message = messageRepository.save(request.toEntity(sender, messageRoom));
		return message.getId();
	}

	private void checkValidOwner(Long currentMemberId, MessageRoom messageRoom){
		if(currentMemberId != messageRoom.getInitialReceiver().getMemberId() && currentMemberId != messageRoom.getInitialSender().getMemberId())
		{
			throw new IllegalArgumentException();
		}
	}


	@Transactional(readOnly = true)
	public Message findById(Long messageId)
	{
		return messageRepository.findById(messageId)
				.orElseThrow(() -> new IllegalArgumentException("해당 message가 없습니다. id=" + messageId));
	}



}
