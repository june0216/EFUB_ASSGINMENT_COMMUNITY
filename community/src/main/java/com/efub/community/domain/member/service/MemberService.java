package com.efub.community.domain.member.service;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.dto.MemberUpdateRequestDto;
import com.efub.community.domain.member.dto.SignUpRequestDto;
import com.efub.community.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class MemberService {
	private final MemberRepository memberRepository;

	public Long signUp(SignUpRequestDto requestDto){
		if (isExistedStudentNo(requestDto.getStudentNo())){
			throw new IllegalArgumentException("이미 존재하는 학번입니다. " + String.valueOf(requestDto.getStudentNo()));
		}
		if (isExistedNickname(requestDto.getNickname())){
			throw new IllegalArgumentException("중복된 닉네임이 있습니다. " + requestDto.getNickname());
		}
		String encodedPassword = requestDto.getPassword();
		Member member = memberRepository.save(requestDto.toEntity(encodedPassword));
		return member.getMemberId();
	}


	public Long update(Long memberId, MemberUpdateRequestDto requestDto){
		if (isExistedNickname(requestDto.getNickname())){
			throw new IllegalArgumentException("중복된 닉네임이 있습니다. " + requestDto.getNickname());
		}
		Member member = findById(memberId);
		member.updateMember(requestDto.getNickname());
		return member.getMemberId();
	}


	public void withdraw(Long memberId) {
		Member member = findById(memberId);
		member.withdraw();
	}

	@Transactional(readOnly = true)
	public Member findById(Long id) {
		return memberRepository.findByMemberId(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 member 를 찾을 수 없습니다. id ="+id));
	}

	@Transactional(readOnly=true)
	public boolean isExistedStudentNo(Integer studentNo){
		return memberRepository.existsByStudentNo(studentNo);
	}
	@Transactional(readOnly = true)
	public boolean isExistedNickname(String nickname){
		return memberRepository.existsByNickname(nickname);
	}



}