package com.efub.community.domain.chat.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRoomResponseDto {
	//조회하는 사람의 id, 받는 사람의 id, 시작 게시글의 id
	private Long memberId;
	private Long receiver;
	private Long createFrom;


}
