package com.efub.community.domain.board.dto.request;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	private Long memberId;

	@NotNull(message = "내용은 필수로 입력되어야 합니다.")//공백 또는 빈칸이 들어올 수 있음, Null이 아닌지만 체크
	private String content;

	@NotNull(message = "익명여부는 필수로 입력해야 합니다.")
	private boolean anonymous;

	@Builder
	public PostRequestDto(String content, boolean anonymous) {
		this.content = content;
		this.anonymous = anonymous;
	}

	public Post toEntity(Member writer, Board board) {
		return Post.builder()
				.anonymous(anonymous)
				.content(content)
				.writer(writer)
				.board(board)
				.build();
	}
}
