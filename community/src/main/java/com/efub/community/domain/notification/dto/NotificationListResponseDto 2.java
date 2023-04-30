package com.efub.community.domain.notification.dto;

import com.efub.community.domain.chat.domain.MessageRoom;
import com.efub.community.domain.chat.dto.MessageRoomListResponseDto;
import com.efub.community.domain.notification.entity.Notification;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationListResponseDto {
	private List<SingleNotification> notifications;
	private Integer count;

	@Getter
	public static class SingleNotification{
		private String notificationType;
		private String content;
		private LocalDateTime createdDate;

		public SingleNotification(Notification notification){
			this.notificationType = notification.getNotificationType().getTitle();
			this.content = notification.getNotificationType().getDescription();
			this.createdDate= notification.getCreatedDate();
		}
		public static NotificationListResponseDto.SingleNotification of(Notification notification) {
			return new NotificationListResponseDto.SingleNotification(notification);
		}

	}

	public static NotificationListResponseDto of(List<Notification> notificationList) {
		return NotificationListResponseDto.builder()
				.notifications(notificationList.stream().map(NotificationListResponseDto.SingleNotification::of).collect(Collectors.toList()))
				.count(notificationList.size())
				.build();
	}

}
