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

	@Column(columnDefinition = "TEXT")// @NotNull은 @Column(nullable=false)의 역할도 같이 하므로 생략
	private String content;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "post_id", insertable = false, updatable = false)
	private Board board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	private boolean anonymous;


	@Builder
	public Post(String content, Member writer, Boolean anonymous, Board board) {
		this.content = content;
		this.writer = writer;
		this.anonymous = anonymous;
		this.board = board;
	}



	public void updatePost(String content)
	{
		this.content = content;
	}

}

