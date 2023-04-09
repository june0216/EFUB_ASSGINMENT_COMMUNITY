package com.efub.community.domain.chat.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRoomListResponseDto {
	//조회하는 사람의 id, 받는 사람의 id, 시작 게시글의 id

	@NotNull
	private Long memberId;
	@NotNull
	private Long receiverId;
	@NotNull
	private Long createFrom;




}
