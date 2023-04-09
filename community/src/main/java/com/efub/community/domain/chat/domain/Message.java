package com.efub.community.domain.chat.domain;

import com.efub.community.domain.board.domain.Post;
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
public class Message extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_room_id", referencedColumnName = "message_room_id")
	private MessageRoom messageRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", referencedColumnName = "member_id")
	private Member sender;

	@Column(name = "content", nullable = false, length = 300)
	private String content;


	@Builder
	public Message(MessageRoom messageRoom, Member sender, String content) {
		this.messageRoom = messageRoom;
		this.sender = sender;
		this.content = content;
	}

	public void setMessageRoom(MessageRoom messageRoom) {
		if (this.messageRoom != null) { // 기존에 존재한다면
			this.messageRoom.getMessages().remove(this); // 관계를 끊는다.
		}
		this.messageRoom = messageRoom;
		messageRoom.getMessages().add(this);
		if(!messageRoom.getMessages().contains(this)) {
			messageRoom.getMessages().add(this);
		}
	}

}
