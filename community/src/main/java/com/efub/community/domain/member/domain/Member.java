package com.efub.community.domain.member.domain;

import com.efub.community.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static com.efub.community.domain.member.domain.MemberStatus.UNREGISTERED;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@DynamicInsert//status 기본값 유지를 위해
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

	@Column(nullable = false, updatable = false, length = 16)
	private String nickname;

	//ToDO: 조건 걸기
	private String studentId;
	private String university;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'REGISTERED'")
	private MemberStatus status;


	@Builder
	public Member(String email, String encodedPassword, String nickname, String studentId, String university, MemberStatus status) {
		this.email = email;
		this.encodedPassword = encodedPassword;
		this.nickname = nickname;
		this.studentId = studentId;
		this.university = university;
		this.status = status;
	}



	public void updateMember(String nickname){
		this.nickname = nickname;
	}

	public void withdraw(){
		this.status = UNREGISTERED;
	}
}
