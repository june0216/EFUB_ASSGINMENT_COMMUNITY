package com.efub.community.domain.chat.dto;

import com.efub.community.domain.chat.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/*
{
    "senderId": 1,
    "messageRoomId": 1,
    "message": "쪽지 내용입니다.",
    "createdDate": "2023-03-22T18:05:56.541863"

}
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResponseDto {
	private Long senderId;
	private Long messageRoomId;
	private String message;
	private LocalDateTime createdDate;

	public MessageResponseDto(Message message) {
		this.senderId = message.getSender().getMemberId();
		this.messageRoomId = message.getMessageRoom().getMessageRoomId();
		this.message = message.getContent();
		this.createdDate = message.getCreatedDate();
	}
}
