package com.efub.community.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
	private  Long memberId;

	public LoginResponseDto(Long accountId) {
		this.memberId = memberId;
	}


}
