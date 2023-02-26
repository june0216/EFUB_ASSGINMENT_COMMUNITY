package com.efub.community.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
	REGISTERED(0, "등록상태", "사용자 등록상태"),
	UNREGISTERED(1, "해지", "사용자 해지상태");

	private final Integer Id;

	private final String title;

	private final String description;
}
