package com.efub.community.domain.chat.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MesssageRoomRequestDto {
	private Long initialSender;
	private Long initialReceiver;
	private Long createdFrom;
	private String message;

	@Builder
	public MesssageRoomRequestDto(Long initialSender, Long initialReceiver, Long createdFrom, String message) {
		this.initialSender = initialSender;
		this.initialReceiver = initialReceiver;
		this.createdFrom = createdFrom;
		this.message = message;
	}
}
