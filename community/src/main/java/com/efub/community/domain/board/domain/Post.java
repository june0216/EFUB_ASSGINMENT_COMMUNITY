package com.efub.community.domain.board.domain;


import com.efub.community.domain.member.domain.Member;
import com.efub.community.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")// @NotNull은 @Column(nullable=false)의 역할도 같이 하므로 생략
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id", updatable = false)
	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", updatable = false)
	private Member writer;


	@Builder
	public Post(String title, String content, Member writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public void updatePost(String title, String content)
	{
		this.title = title;
		this.content = content;
	}

}

