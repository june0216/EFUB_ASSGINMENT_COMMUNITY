package com.efub.community.domain.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.efub.community.domain.member.domain.MemberStatus.REGISTERED;
import static com.efub.community.domain.member.domain.MemberStatus.UNREGISTERED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberTest {



	@Test
	void createMember_WithInvalidEmail_ShouldThrowException() {
		final String INVALID_EMAIL = "testgmail.com";
		final String TEST_NICKNAME = "테스트계정";
		final String TEST_ENCODED_PASSWORD = "encodedPassword";
		final String TEST_UNIVERSITY = "이화여자대학교";
		final Integer TEST_STUDENT_ID = 1989001;
		final MemberStatus EXPECTED_STATUS = REGISTERED;

		Member member = createMember(INVALID_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);

		assertThat(member.getEmail()).isEqualTo(INVALID_EMAIL);
		assertThat(member.getNickname()).isEqualTo(TEST_NICKNAME);
		assertThat(member.getEncodedPassword()).isEqualTo(TEST_ENCODED_PASSWORD);
		assertThat(member.getUniversity()).isEqualTo(TEST_UNIVERSITY);
		assertThat(member.getStudentNo()).isEqualTo(TEST_STUDENT_ID);
		assertThat(member.getStatus()).isEqualTo(EXPECTED_STATUS);
	}


	@Test
	void updateMember_GivenRegisteredMember_ShouldChangeNickname() {
		final String INITIAL_NICKNAME = "테스트계정";
		final String UPDATED_NICKNAME = "새테스트계정";

		Member member = createDefaultMember();
		member.updateMember(UPDATED_NICKNAME);

		assertThat(member.getNickname()).isEqualTo(UPDATED_NICKNAME);
	}


	@Test
	void withdraw_GivenRegisteredMember_ShouldSetStatusToUnregistered() {
		final MemberStatus EXPECTED_STATUS = UNREGISTERED;

		Member member = createDefaultMember();
		member.withdraw();

		assertThat(member.getStatus()).isEqualTo(EXPECTED_STATUS);
	}

	private Member createDefaultMember() {
		final String TEST_EMAIL = "test@gmail.com";
		final String TEST_NICKNAME = "테스트계정";
		final String TEST_ENCODED_PASSWORD = "encodedPassword";
		final String TEST_UNIVERSITY = "이화여자대학교";
		final Integer TEST_STUDENT_NO = 1989001;

		return Member.builder()
				.email(TEST_EMAIL)
				.nickname(TEST_NICKNAME)
				.encodedPassword(TEST_ENCODED_PASSWORD)
				.university(TEST_UNIVERSITY)
				.studentNo(TEST_STUDENT_NO)
				.build();
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