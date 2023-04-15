package com.efub.community.domain.chat.service;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.service.PostService;
import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
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
public class MessageRoomService {

	private final MessageRoomRepository messageRoomRepository;
	private final MemberService memberService;
	private final PostService postService;
	private final MessageRepository messageRepository;


	public Long createMessageRoom(MessageRoomRequestDto messageRoomRequestDto)
	{
		Member sender = memberService.findById(messageRoomRequestDto.getInitialSender());
		Member receiver = memberService.findById(messageRoomRequestDto.getInitialReceiver());
		Post createdFrom = postService.findById(messageRoomRequestDto.getCreatedFrom());
		MessageRoom messageRoom = MessageRoom.builder()
			.initialSender(sender)
			.initialReceiver(receiver)
			.createdFrom(createdFrom)
			.build();
		Message message = Message.builder()
				.content(messageRoomRequestDto.getMessage())
				.sender(sender)
				.build();
		message.setMessageRoom(messageRoom);
		messageRoomRepository.save(messageRoom);
		messageRepository.save(message);
		return messageRoom.getMessageRoomId();

	}

	public Long checkMessageRoom(Long senderId, Long receiverId, Long createdFrom){
		MessageRoom messageRoom = findByInitialSenderAndInitialReceiverAndCreatedFrom(senderId, receiverId, createdFrom);
		return messageRoom.getMessageRoomId();
	}

	public void delete(Long messageRoomId, Long memberId){
		MessageRoom messageRoom = findById(messageRoomId);
		if(checkValidMember(memberId, messageRoom.getInitialReceiver().getMemberId()) || checkValidMember(memberId, messageRoom.getInitialSender().getMemberId())){
			messageRoomRepository.delete(messageRoom);
		}
		else{
			throw new IllegalArgumentException();
		}

	}

	private boolean checkValidMember(Long currentMemberId, Long tagetMemberId){
		if(currentMemberId == tagetMemberId)
		{
			return true;
		}
		return false;
	}


	@Transactional(readOnly = true)
	public MessageRoom findById(Long messageRoomId){
		return messageRoomRepository.findById(messageRoomId)
				.orElseThrow(() -> new IllegalArgumentException("해당 messageRoom이 없습니다. id=" + messageRoomId));
	}

	@Transactional(readOnly = true)
	public List<MessageRoom> findByOwner(Long memberId) {
		Member owner = memberService.findById(memberId);
		return messageRoomRepository.findByInitialSenderOrInitialReceiver(owner, owner);
	}

	@Transactional(readOnly = true)
	public MessageRoom findByInitialSenderAndInitialReceiverAndCreatedFrom(Long senderId, Long receiverId, Long createdFrom){
		return messageRoomRepository.findByInitialReceiverAndInitialSenderAndCreatedFrom(senderId, receiverId, createdFrom);
	}

}
