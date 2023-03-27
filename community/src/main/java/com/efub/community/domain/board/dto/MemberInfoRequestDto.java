package com.efub.community.domain.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoRequestDto {//로그인 대용 임시방편 DTO
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	private Long memberId;

	@Builder
	public MemberInfoRequestDto(Long memberId) {
		this.memberId = memberId;
	}
}
