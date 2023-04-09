package com.efub.community.domain.board.dto.request;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

	@NotBlank(message = "제목은 필수로 입력되어야 합니다.")
	private String name;

	@NotNull(message = "멤버 id는 필수로 입력되어야 합니다. ")
	private Long memberId;
	private String description;

	public Board toEntity(Member member){
		return Board.builder()
				.name(name)
				.owner(member)
				.description(description)
				.build();
	}
}


