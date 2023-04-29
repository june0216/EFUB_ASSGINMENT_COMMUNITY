package com.efub.community.domain.notification.service;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.service.MemberService;
import com.efub.community.domain.notification.entity.Notification;
import com.efub.community.domain.notification.entity.NotificationType;
import com.efub.community.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class NotificationService {

	private NotificationRepository notificationRepository;
	private MemberService memberService;

	public List<Notification> readAll(Long currentMemberId){
		Member member = memberService.findById(currentMemberId);
		return findByMemberId(member.getMemberId());
	}

	public void createNotification(NotificationType notificationType, Member member){
		Notification notification = Notification.builder()
				.notificationType(notificationType)
				.member(member)
				.build();
		notificationRepository.save(notification);
	}


	@Transactional(readOnly = true)
	public List<Notification> findByMemberId(Long memberId)
	{
		return findByMemberId(memberId);
	}

}
