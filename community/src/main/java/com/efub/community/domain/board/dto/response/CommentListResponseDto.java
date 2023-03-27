package com.efub.community.domain.board.dto.response;

import com.efub.community.domain.board.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentListResponseDto {
	//게시글에 해당하는 댓글 목록 반환 dto
	@Getter
	@Builder
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Post {


		private Long postId;

		private List<SingleComment> comments;

		private Integer count;


		@Getter
		public static class SingleComment{//해당 post에 있는 단일 댓글


			private Long commentId;

			private String writerName;

			private String content;

			private LocalDateTime createDate;

			private LocalDateTime modifiedDate;

			public SingleComment(Comment comment) {
				this.commentId = comment.getCommentId();
				if(comment.isAnonymous()){
					this.writerName = "익명" + commentId;
				}
				else{
					this.writerName = comment.getWriter().getNickname();
				}
				this.content = comment.getContent();
				this.createDate = comment.getCreatedDate();
				this.modifiedDate = comment.getModifiedDate();
			}

			public static SingleComment of(Comment comment) {
				return new SingleComment(comment);
			}
		}

		public static CommentListResponseDto.Post of(Long postId, List<Comment> commentList) {
			return CommentListResponseDto.Post.builder()
					.postId(postId)
					.comments(commentList.stream().map(SingleComment::of).collect(Collectors.toList()))
					.count(commentList.size())
					.build();
		}
	}

	//해당 작성자 댓글 전체 조회
}
