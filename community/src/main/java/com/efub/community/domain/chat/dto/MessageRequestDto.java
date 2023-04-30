package com.efub.community.domain.chat.dto;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDto {
	@NotNull
	private String content;
	@NotNull
	private Long senderId;

	@Builder
	public MessageRequestDto(String content, Long senderId) {
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
