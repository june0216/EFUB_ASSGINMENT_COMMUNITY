package com.efub.community.domain.chat.repository;

import com.efub.community.domain.chat.domain.Message;
import com.efub.community.domain.chat.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
