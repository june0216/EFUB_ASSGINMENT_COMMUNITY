package com.efub.community.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
	COMMENT(1, "댓글", "새로운 댓글이 달렸습니다."),
	MESSAGEROOM(2, "쪽지방", "새로운 쪽지방이 생성되었습니다.");

	private final Integer Id;

	private final String title;

	private final String description;
}
