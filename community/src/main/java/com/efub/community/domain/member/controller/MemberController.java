package com.efub.community.domain.member.controller;

import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.dto.*;
import com.efub.community.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public MemberResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
		Long id = memberService.signUp(requestDto);
		Member findMember = memberService.findById(id);
		return new MemberResponseDto(findMember);
	}

	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public LoginResponseDto login(@RequestBody final LoginRequestDto requestDto)
	{
		Long memberId = memberService.login(requestDto);
		return new LoginResponseDto(memberId);
	}
	@GetMapping("/{memberId}")
	@ResponseStatus(value = HttpStatus.OK)
	public MemberResponseDto getMember(@PathVariable Long memberId)
	{
		Member findMember = memberService.findById(memberId);
		return new MemberResponseDto(findMember);
	}


	@PatchMapping("/profile/{memberId}")
	@ResponseStatus(value = HttpStatus.OK)
	public MemberResponseDto update(@PathVariable final Long memberId, @RequestBody @Valid final MemberUpdateRequestDto requestDto) {
		Long id = memberService.update(memberId,requestDto);
		Member findMember = memberService.findById(memberId);
		return new MemberResponseDto(findMember);
	}

	@PatchMapping("/{memberId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String withdraw(@PathVariable long memberId)
	{
		memberService.withdraw(memberId);
		return "성공적으로 탈퇴가 완료되었습니다";
	}

}
