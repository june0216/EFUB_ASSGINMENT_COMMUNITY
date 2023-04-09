package com.efub.community.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {
	@NotNull(message = "학번을 필수 입니다.")
	private Integer studentNo;

	@NotBlank(message = "비밀번호는 필수입니다.")//해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!.?,])[A-Za-z\\d!.?,]{2,16}$",
			message = "16자 이내의 영문자 및 숫자와 ?,!,., , 특수문자로 입력해주세요.")
	private String password;

	@Builder
	public LoginRequestDto(Integer studentNo, String password) {
		this.studentNo = studentNo;
		this.password = password;
	}
}
