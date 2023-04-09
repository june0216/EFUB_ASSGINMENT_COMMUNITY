package com.efub.community.domain.board.domain;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@Column(length = 1000)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	private Member writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", updatable = false)
	private Post post;
	private boolean anonymous;


	@Builder
	public Comment(String content, Member writer, Post post, boolean anonymous) {
		this.content = content;
		this.writer = writer;
		this.post = post;
		this.anonymous =anonymous;
	}

	public void setPost(Post post) {
		if (this.post != null) { // 기존에 존재한다면
			this.post.getCommentList().remove(this); // 관계를 끊는다.
		}
		this.post = post;
		if(!post.getCommentList().contains(this)) {
			post.getCommentList().add(this);
		}
	}
	public void updateComment(String content) {
		this.content = content;
	}
}
