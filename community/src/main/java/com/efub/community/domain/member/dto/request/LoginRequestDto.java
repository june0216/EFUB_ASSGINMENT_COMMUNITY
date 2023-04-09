package com.efub.community.domain.member.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {
	private Integer studentNo;

	private String password;

	@Builder
	public LoginRequestDto(Integer studentNo, String password) {
		this.studentNo = studentNo;
		this.password = password;
	}
}
