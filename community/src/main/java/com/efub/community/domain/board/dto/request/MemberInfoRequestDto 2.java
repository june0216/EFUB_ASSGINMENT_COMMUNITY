package com.efub.community.domain.board.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoRequestDto {
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	private Long memberId;

	public MemberInfoRequestDto(Long memberId) {
		this.memberId = memberId;
	}
}
