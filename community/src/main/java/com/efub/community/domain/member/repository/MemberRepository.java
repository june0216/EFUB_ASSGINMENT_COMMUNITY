package com.efub.community.domain.member.repository;

import com.efub.community.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByMemberId(Long memberId);

	Optional<Member> findByStudentNo(Integer studentNo);
	Boolean existsByStudentNo(Integer studentNo);
	Boolean existsByNickname(String nickname);
}
