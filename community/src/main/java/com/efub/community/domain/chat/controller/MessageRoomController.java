package com.efub.community.domain.chat.controller;

import com.efub.community.domain.chat.dto.MessageRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/messagerooms")
@RequiredArgsConstructor
public class MessageRoomController {

	//public MessageRoomResponseDto createMessageRoom(@RequestBody @Valid Mess)
}
