package com.efub.community.domain.board.dto.response;

import com.efub.community.domain.board.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto {
	private Long commentId;

	private Long postId;

	private String writerName;

	private String content;
	private Integer heartCount;
	private Boolean isHeart;


	private LocalDateTime modifiedDate;

	public static CommentResponseDto of(Comment comment) {
		String writerName;
		if(comment.isAnonymous())
		{
			writerName = "익명" + comment.getCommentId();
		}
		else{
			writerName = comment.getWriter().getNickname();
		}
		return CommentResponseDto.builder()
				.commentId(comment.getCommentId())
				.postId(comment.getPost().getPostId())
				.writerName(writerName)
				.content(comment.getContent())
				.modifiedDate(comment.getModifiedDate())
				.build();
	}
	public void uploadHeart(Integer heartCount, boolean isHeart) {
		this.heartCount = heartCount;
		this.isHeart = isHeart;

	}
}
