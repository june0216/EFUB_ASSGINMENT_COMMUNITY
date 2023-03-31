package com.efub.community.domain.chat.dto;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDto {
	private Long messageRoomId;
	private String content;
	private Long senderId;

	@Builder
	public MessageRequestDto(Long messageRoomId, String content, Long senderId) {
		this.messageRoomId = messageRoomId;
		this.content = content;
		this.senderId = senderId;
	}

	public Message toEntity(Member sender, MessageRoom messageRoom)
	{
		return Message.builder()
				.content(content)
				.sender(sender)
				.messageRoom(messageRoom)
				.build();
	}

}
