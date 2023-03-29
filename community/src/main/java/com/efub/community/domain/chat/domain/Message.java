package com.efub.community.domain.chat.domain;

import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
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
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long messageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initial_sender_id", referencedColumnName = "user_id")
	private Member initialSender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initial_receiver_id", referencedColumnName = "user_id")
	private Member initialReceiver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_from", referencedColumnName = "post_id")
	private Post createdFrom;

	@Column(name = "is_anonymous", nullable = false)
	private Boolean isAnonymous;

	@OneToMany(mappedBy = "messageRoom")
	private List<Message> messages = new ArrayList<>();

	@Builder
	public Message(Member initialSender, Member initialReceiver, Post createdFrom, Boolean isAnonymous, List<Message> messages) {
		this.initialSender = initialSender;
		this.initialReceiver = initialReceiver;
		this.createdFrom = createdFrom;
		this.isAnonymous = isAnonymous;
		this.messages = messages;
	}
}