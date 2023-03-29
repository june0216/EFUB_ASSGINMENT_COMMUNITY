package com.efub.community.domain.chat.repository;

import com.efub.community.domain.chat.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
}
