package com.efub.community.domain.chat.dto;

import com.efub.community.domain.chat.domain.MessageRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomResponseDto {

	private Long initialSender;
	private Long initialReceiver;
	private String message;
	private Long createdFrom;
	private Long messageRoomId;
	private LocalDateTime createdDate;

	@Builder
	public MessageRoomResponseDto(MessageRoom messageRoom) {
		this.initialSender = messageRoom.getInitialSender().getMemberId();
		this.initialReceiver = messageRoom.getInitialReceiver().getMemberId();
		this.message = messageRoom.getLatestMessage().getContent();
		this.createdFrom = messageRoom.getCreatedFrom().getPostId();
		this.messageRoomId = messageRoom.getMessageRoomId();
		this.createdDate = messageRoom.getCreatedDate();
	}
}
