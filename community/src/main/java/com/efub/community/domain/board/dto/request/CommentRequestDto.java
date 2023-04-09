package com.efub.community.domain.board.dto.request;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	private Long memberId;
	@NotNull(message = "내용은 필수로 입력되어야 합니다.")// 공백까지는 허용
	private String content;

	@NotNull(message = "익명 여부는 필수로 입력되어야 합니다.")
	private boolean anonymous;

	@Builder
	public CommentRequestDto(Long memberId, String content, boolean anonymous ) {
		this.memberId = memberId;
		this.content = content;
		this.anonymous = anonymous;
	}



	public Comment toEntity(Member account)
	{
		return Comment.builder()
				.content(this.content)
				.anonymous(this.anonymous)
				.writer(account)
				.build();

	}
}
