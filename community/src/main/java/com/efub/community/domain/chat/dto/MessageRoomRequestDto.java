package com.efub.community.domain.chat.dto;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomRequestDto {
	@NotNull
	private Long initialSender;
	@NotNull
	private Long initialReceiver;
	@NotNull
	private Long createdFrom;
	@NotNull
	private String message;

	@Builder
	public MessageRoomRequestDto(Long initialSender, Long initialReceiver, Long createdFrom, String message) {
		this.initialSender = initialSender;
		this.initialReceiver = initialReceiver;
		this.createdFrom = createdFrom;
		this.message = message;
	}

	public MessageRoom toEntity(Member initialReceiver, Member initialSender, Post createdFrom, List<Message> messages){
		return MessageRoom.builder()
				.initialSender(initialSender)
				.initialReceiver(initialReceiver)
				.createdFrom(createdFrom)
				.messages(messages)
				.build();
	}
}
