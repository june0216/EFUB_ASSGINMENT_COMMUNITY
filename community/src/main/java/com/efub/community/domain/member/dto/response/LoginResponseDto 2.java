package com.efub.community.domain.member.dto.response;


import lombok.AccessLevel;
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
