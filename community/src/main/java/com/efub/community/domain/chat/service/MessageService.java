package com.efub.community.domain.chat.service;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.service.PostService;
import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRoomRequestDto;
import com.efub.community.domain.chat.repository.MessageRoomRepository;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class MessageService {
	private final MemberService memberService;
	private final PostService postService;
	private final MessageRoomRepository messageRoomRepository;
	public Long createMessageRoom(MessageRoomRequestDto request) {
		Member sender = memberService.findById(request.getInitialSender());
		Member receiver = memberService.findById(request.getInitialReceiver());
		Post createdFrom = postService.findById(request.getCreatedFrom());
		MessageRoom messageRoom = messageRoomRepository.save(request.toEntity(receiver, sender, createdFrom));
		return messageRoom.getMessageRoomId();
	}

}
