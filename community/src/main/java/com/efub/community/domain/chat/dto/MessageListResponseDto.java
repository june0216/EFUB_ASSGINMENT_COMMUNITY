package com.efub.community.domain.chat.dto;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.member.domain.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageListResponseDto {
	private Long messageRoomId;
	private Long receiverId;
	private List<MessageListResponseDto.SingleMessage> messages;
	private Integer count;

	@Getter
	public static class SingleMessage{

		private boolean isReceived;

		private String message;

		private LocalDateTime createdDate;

		public SingleMessage(Message message, Member currentMember) {
			this.isReceived = currentMember.getMemberId() != message.getSender().getMemberId() ? true : false;
			this.message = message.getContent();
			this.createdDate = message.getCreatedDate();
		}

		public static MessageListResponseDto.SingleMessage of(Message message, Member currentMember) {
			return new MessageListResponseDto.SingleMessage(message, currentMember);
		}
	}

	public static MessageListResponseDto of(List<Message> messageList, MessageRoom messageRoom, Member currentMember) {
		return MessageListResponseDto.builder()
				.messageRoomId(messageRoom.getMessageRoomId())
				.receiverId(currentMember.getMemberId().equals(messageRoom.getInitialSender().getMemberId())
						? messageRoom.getInitialReceiver().getMemberId()
						: messageRoom.getInitialSender().getMemberId())
				.messages(messageList.stream()
						.map(m -> SingleMessage.of(m, currentMember))
						.collect(Collectors.toList()))
				.count(messageList.size())
				.build();
	}



}
