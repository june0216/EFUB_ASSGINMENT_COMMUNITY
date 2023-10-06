package com.efub.community.domain.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;


import static com.efub.community.domain.member.domain.MemberStatus.UNREGISTERED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemberTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}


	@Test
	@DisplayName("회원_수정_성공")
	void updateMember_GivenRegisteredMember_ShouldChangeNickname() {
		final String INITIAL_NICKNAME = "테스트계정";
		final String UPDATED_NICKNAME = "새테스트계정";

		Member member = createDefaultMember();
		member.updateMember(UPDATED_NICKNAME);

		assertThat(member.getNickname()).isEqualTo(UPDATED_NICKNAME);
	}


	@Test
	@DisplayName("회원탈퇴_성공")
	void withdraw_GivenRegisteredMember_ShouldSetStatusToUnregistered() {
		final MemberStatus EXPECTED_STATUS = UNREGISTERED;

		Member member = createDefaultMember();
		member.withdraw();

		assertThat(member.getStatus()).isEqualTo(EXPECTED_STATUS);
	}

	@Test
	@DisplayName("닉네임검사_성공")
	void whenNicknameIsWithinLengthLimit_thenShouldNotHaveConstraintViolations() {
		final String INVALID_EMAIL = "test@gmail.com";
		final String TEST_NICKNAME = "정당한닉네임";
		final String TEST_ENCODED_PASSWORD = "encodedPassword";
		final String TEST_UNIVERSITY = "이화여자대학교";
		final Integer TEST_STUDENT_ID = 1989001;

		Member member = createMember(INVALID_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);


		Set<ConstraintViolation<Member>> violations = validator.validate(member);
		assertTrue(violations.isEmpty());
	}
	@Test
	@DisplayName("이메일형식검사_실패")
	void whenEmailIsInvalid_thenShouldHaveConstraintViolations() {
		final String INVALID_EMAIL = "testgmail.com";
		final String TEST_NICKNAME = "테스트계정";
		final String TEST_ENCODED_PASSWORD = "encodedPassword";
		final String TEST_UNIVERSITY = "이화여자대학교";
		final Integer TEST_STUDENT_ID = 1989001;

		Member member = createMember(INVALID_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);


		Set<ConstraintViolation<Member>> violations = validator.validate(member);
		assertEquals(1, violations.size());
		assertEquals("유효하지 않은 이메일 형식입니다.", violations.iterator().next().getMessage());
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