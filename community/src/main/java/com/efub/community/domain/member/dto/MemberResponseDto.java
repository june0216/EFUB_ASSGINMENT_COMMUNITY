package com.efub.community.domain.member.dto;

import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {
	private Long accountId;
	private String email;
	private String nickname;
	private String university;
	private String studentId;

	@Builder
	public MemberResponseDto(Member member) {
		this.accountId = member.getMemberId();
		this.email = getEmail();
		this.nickname = getNickname();
		this.university = getUniversity();
		this.studentId = getStudentId();
	}
}
