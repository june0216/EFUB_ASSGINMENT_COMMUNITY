package com.efub.community.domain.member.repository;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.efub.community.domain.member.domain.MemberStatus.REGISTERED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach//JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드, 다음 테스트에 H2에 남아있는 데이터가 영향을 주지 않기 위해 삭제
    public void cleanUp()
    {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("계정_생성_성공")
    void createMember_GivenValidInfo_ShouldSetAttributesCorrectly() {
        final String TEST_EMAIL = "test@gmail.com";
        final String TEST_NICKNAME = "테스트계정";
        final String TEST_ENCODED_PASSWORD = "encodedPassword";
        final String TEST_UNIVERSITY = "이화여자대학교";
        final Integer TEST_STUDENT_ID = 1989001;
        final MemberStatus EXPECTED_STATUS = REGISTERED;
        Member member = createMember(TEST_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);

        //when
        Member saveMember = memberRepository.save(member);

        //then
        //assertThat(대상).isEqualTo(기대하는 값)
        assertThat(saveMember.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(saveMember.getNickname()).isEqualTo(TEST_NICKNAME);
        assertThat(saveMember.getEncodedPassword()).isEqualTo(TEST_ENCODED_PASSWORD);
        assertThat(saveMember.getUniversity()).isEqualTo(TEST_UNIVERSITY);
        assertThat(saveMember.getStudentNo()).isEqualTo(TEST_STUDENT_ID);
        assertThat(saveMember.getStatus()).isEqualTo(EXPECTED_STATUS);
    }




    @Test
    @DisplayName("계정_조회_실패_존재하지않는ID")
    void findByMemberId_ShouldReturnEmpty_WhenMemberDoesNotExist() {
        // Given
        Long nonExistentId = 9999L;

        // When
        Optional<Member> foundMember = memberRepository.findByMemberId(nonExistentId);

        // Then
        assertThat(foundMember).isEmpty();
    }

    @Test
    @DisplayName("학번_존재여부_성공")
    void existsByStudentNo_GivenExistingStudentNo_ShouldReturnTrue() {
        final String TEST_EMAIL = "test1@gmail.com";
        final String TEST_NICKNAME = "테스트계정1";
        final String TEST_ENCODED_PASSWORD = "encodedPassword";
        final String TEST_UNIVERSITY = "이화여자대학교";
        final Integer TEST_STUDENT_ID = 1989002;

        Member member = createMember(TEST_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);
        memberRepository.save(member);

        boolean exists = memberRepository.existsByStudentNo(TEST_STUDENT_ID);

        assertTrue(exists);
    }


    @Test
    @DisplayName("학번_존재여부_실패")
    void existsByStudentNo_GivenNonExistingStudentNo_ShouldReturnFalse() {
        final Integer NON_EXISTING_STUDENT_ID = 1989010;

        boolean exists = memberRepository.existsByStudentNo(NON_EXISTING_STUDENT_ID);

        assertFalse(exists);
    }

    @Test
    @DisplayName("계정_생성_중복이메일_실패")
    void createMember_WithDuplicateEmail_ShouldThrowException() {
        final String DUPLICATE_EMAIL = "duplicate@gmail.com";
        final String TEST_NICKNAME = "테스트계정";
        final String TEST_NICKNAME_SECOND = "테스트계정2";
        final String TEST_ENCODED_PASSWORD = "encodedPassword";
        final String TEST_UNIVERSITY = "이화여자대학교";
        final Integer TEST_STUDENT_ID = 1989001;

        Member firstMember = createMember(DUPLICATE_EMAIL, TEST_NICKNAME, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);
        memberRepository.save(firstMember);

        Member secondMember = createMember(DUPLICATE_EMAIL, TEST_NICKNAME_SECOND, TEST_ENCODED_PASSWORD, TEST_UNIVERSITY, TEST_STUDENT_ID);

        assertThrows(DataIntegrityViolationException.class, () -> {
            memberRepository.save(secondMember);
            memberRepository.flush(); // 즉시 쿼리 실행
        });

        testEntityManager.clear(); // 여기서 영속성 컨텍스트 초기화
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