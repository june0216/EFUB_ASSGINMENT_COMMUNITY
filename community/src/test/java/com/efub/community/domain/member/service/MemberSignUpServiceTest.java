package com.efub.community.domain.member.service;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;
import com.efub.community.domain.member.dto.request.SignUpRequestDto;
import com.efub.community.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MemberSignUpServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    private Member member;

    @BeforeEach
    public void setUp() throws Exception {
        member = MemberBuilder.build();
    }

    @Test
    @DisplayName("회원가입_성공")
    void signUp_GivenRegisteredMember_RetrunSavedMemberId() {
        //given
        final String VALID_EMAIL = member.getEmail();
        final String VALID_NICKNAME = member.getNickname();
        final String VALID_ENCODED_PASSWORD = member.getEncodedPassword();
        final String VALID_UNIVERSITY = member.getUniversity();
        final Integer VALID_STUDENT_NO = member.getStudentNo();

        final SignUpRequestDto dto = SignUpRequestDto.builder()
                .email(VALID_EMAIL)
                .university(VALID_UNIVERSITY)
                .nickname(VALID_NICKNAME)
                .password(VALID_ENCODED_PASSWORD)
                .studentNo(VALID_STUDENT_NO)
                .build();

        given(memberRepository.save(any(Member.class))).willReturn(member);

        //when
        final Long signUpMemberId = memberService.signUp(dto);

        //then
        assertThat(signUpMemberId).isEqualTo(member.getMemberId());
    }


}