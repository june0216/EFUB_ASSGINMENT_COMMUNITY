package com.efub.community.domain.member.domain;

import com.efub.community.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.efub.community.domain.member.domain.MemberStatus.REGISTERED;
import static com.efub.community.domain.member.domain.MemberStatus.UNREGISTERED;


@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", updatable = false)
	private Long memberId;

	@Column(nullable = false, length = 60)//DB에 저장될 때 조건(물리적인 데이터베이스 컬럼의 특성을 나타냄), 유효성 체크를 해주지는 않음
	private String email;


	@Column(nullable = false)
	private String encodedPassword;

	@Column(nullable = false, length = 16)
	private String nickname;

	@Column(nullable = false, updatable = false)
	private Integer studentNo;
	@Column(nullable = false, updatable = false)
	private String university;

	@Enumerated(EnumType.STRING)
	private MemberStatus status;


	@Builder
	public Member(String email, String encodedPassword, String nickname,Integer studentNo, String university) {
		this.email = email;
		this.encodedPassword = encodedPassword;
		this.nickname = nickname;
		this.studentNo = studentNo;
		this.university = university;
		this.status = REGISTERED;
	}



	public void updateMember(String nickname){
		this.nickname = nickname;
	}

	public void withdraw(){
		this.status = UNREGISTERED;
	}
}