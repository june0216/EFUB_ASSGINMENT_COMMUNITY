package com.efub.community.domain.notification.controller;

import com.efub.community.domain.notification.dto.NotificationListResponseDto;
import com.efub.community.domain.notification.entity.Notification;
import com.efub.community.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public NotificationListResponseDto getNotificationList(@RequestParam final Long memberId)
	{
		List<Notification> notifications = notificationService.readAll(memberId);
		return NotificationListResponseDto.of(notifications);

	}

}
