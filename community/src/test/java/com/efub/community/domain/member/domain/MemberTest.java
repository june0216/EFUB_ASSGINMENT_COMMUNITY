package com.efub.community.domain.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.efub.community.domain.member.domain.MemberStatus.REGISTERED;
import static com.efub.community.domain.member.domain.MemberStatus.UNREGISTERED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberTest {

	@Test
	void 계정_생성_테스트() {
		String email = "test@gmail.com";
		String nickname = "테스트계정";
		String encodedPassword = "encodedPassword";
		String university = "이화여자대학교";
		Integer studentId = 1989001;
		MemberStatus status = REGISTERED;

		Member member = createMember(email, nickname, encodedPassword, university,studentId);

		assertThat(member.getEmail()).isEqualTo(email);
		assertThat(member.getNickname()).isEqualTo(nickname);
		assertThat(member.getEncodedPassword()).isEqualTo(encodedPassword);
		assertThat(member.getUniversity()).isEqualTo(university);
		assertThat(member.getStudentNo()).isEqualTo(studentId);
		assertThat(member.getStatus()).isEqualTo(status);
	}

	@Test
	void 계정_업데이트_테스트() {
		String email = "test@gmail.com";
		String nickname = "테스트계정";
		String encodedPassword = "encodedPassword";
		String university = "이화여자대학교";
		Integer studentId = 1989001;
		String newNickname = "새테스트계정";

		Member member = createMember(email, nickname, encodedPassword, university, studentId);

		member.updateMember(newNickname);

		assertThat(member.getNickname()).isEqualTo(newNickname);

	}

	@Test
	void 계정_삭제_테스트() {
		String email = "test@gmail.com";
		String nickname = "테스트계정";
		String encodedPassword = "encodedPassword";
		String university = "이화여자대학교";
		Integer studentNo = 1989001;
		MemberStatus status = UNREGISTERED;

		Member member = createMember(email, nickname, encodedPassword, university,studentNo);
		member.withdraw();

		assertThat(member.getEmail()).isEqualTo(email);
		assertThat(member.getNickname()).isEqualTo(nickname);
		assertThat(member.getEncodedPassword()).isEqualTo(encodedPassword);
		assertThat(member.getUniversity()).isEqualTo(university);
		assertThat(member.getStudentNo()).isEqualTo(studentNo);
		assertThat(member.getStatus()).isEqualTo(status);
	}



	private Member createMember(String email, String name, String encodedPassword, String university, Integer studentNo) {
		Member member = Member.builder()
				.email(email)
				.nickname(name)
				.encodedPassword(encodedPassword)
				.university(university)
				.studentNo(studentNo)
				.build();
		return member;
	}
}