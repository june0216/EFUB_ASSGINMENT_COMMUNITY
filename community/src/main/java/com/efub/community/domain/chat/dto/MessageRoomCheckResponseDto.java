package com.efub.community.domain.chat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomCheckResponseDto {
	private Long messageRoomId;

	public MessageRoomCheckResponseDto(Long messageRoomId) {
		this.messageRoomId = messageRoomId;
	}
}
