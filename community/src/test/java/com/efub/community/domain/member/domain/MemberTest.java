package com.efub.community.domain.member.domain;

import org.aspectj.lang.annotation.Before;
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
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

	@Test
	@DisplayName("닉네임_업데이트_성공")
	void updateMember_GivenRegisteredMember_ShouldChangeNickname() {
		final String UPDATED_NICKNAME = "새테스트계정";

		final Member member = MemberBuilder.build();
		member.updateMember(UPDATED_NICKNAME);

		assertThat(member.getNickname()).isEqualTo(UPDATED_NICKNAME);
	}

	@Test
	@DisplayName("닉네임_업데이트_실패_null")
	void updateMember_ShouldFail_WhenNicknameIsNull() {
		final String nullNickname = null;
		final Member member = MemberBuilder.build();

		assertThrows(IllegalArgumentException.class, () -> member.updateMember(nullNickname));
	}

	@Test
	@DisplayName("닉네임_업데이트_실패_length_over_16")
	void updateMember_ShouldFail_WhenNicknameIsTooLong() {
		final String tooLongNickname = "ThisNicknameIsWayTooLong";
		final Member member = MemberBuilder.build();

		assertThrows(IllegalArgumentException.class, () -> member.updateMember(tooLongNickname));
	}


	@Test
	@DisplayName("회원탈퇴_성공")
	void withdraw_GivenRegisteredMember_ShouldSetStatusToUnregistered() {
		final MemberStatus EXPECTED_STATUS = UNREGISTERED;

		final Member member = MemberBuilder.build();
		member.withdraw();

		assertThat(member.getStatus()).isEqualTo(EXPECTED_STATUS);
	}

}