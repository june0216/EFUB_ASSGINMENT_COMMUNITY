package com.efub.community.domain.notification.repository;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByMember(Member member);
}
