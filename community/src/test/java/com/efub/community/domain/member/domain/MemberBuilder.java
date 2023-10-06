package com.efub.community.domain.member.domain;

public class MemberBuilder {
    public static Member build(){
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

    public static Member build(Long memberId){
        final String TEST_EMAIL = "test@gmail.com";
        final String TEST_NICKNAME = "테스트계정";
        final String TEST_ENCODED_PASSWORD = "encodedPassword";
        final String TEST_UNIVERSITY = "이화여자대학교";
        final Integer TEST_STUDENT_NO = 1989001;

        final Member member =  Member.builder()
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .encodedPassword(TEST_ENCODED_PASSWORD)
                .university(TEST_UNIVERSITY)
                .studentNo(TEST_STUDENT_NO)
                .build();
        member.setMemberId(memberId);
        return member;
    }

}
