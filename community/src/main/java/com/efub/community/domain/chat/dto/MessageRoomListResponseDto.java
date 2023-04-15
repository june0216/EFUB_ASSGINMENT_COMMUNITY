package com.efub.community.domain.chat.dto;


import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.dto.response.PostListResponseDto;
import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRoomListResponseDto {
	//조회하는 사람의 id, 받는 사람의 id, 시작 게시글의 id

	private List<MessageRoomListResponseDto.SingleMessageRoom> messageRooms;
	private Integer count;

	@Getter
	public static class SingleMessageRoom{

		private Long messagRoomId;

		private String message;

		private LocalDateTime createdDate;

		public SingleMessageRoom(MessageRoom messageRoom) {
			this.messagRoomId = messageRoom.getMessageRoomId();
			Message latestMessage = messageRoom.getLatestMessage();
			this.message = latestMessage.getContent();
			this.createdDate = latestMessage.getCreatedDate();
		}

		public static MessageRoomListResponseDto.SingleMessageRoom of(MessageRoom messageRoom) {
			return new MessageRoomListResponseDto.SingleMessageRoom(messageRoom);
		}
	}


	public static MessageRoomListResponseDto of(List<MessageRoom> messageRoomList) {
		return MessageRoomListResponseDto.builder()
				.messageRooms(messageRoomList.stream().map(MessageRoomListResponseDto.SingleMessageRoom::of).collect(Collectors.toList()))
				.count(messageRoomList.size())
				.build();
	}




}
