package com.efub.community.domain.board.domain;

import com.efub.community.domain.board.domain.builder.CommentBuilder;
import com.efub.community.domain.board.domain.builder.PostBuilder;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Post validPost;
    private Member validWriter;
    @BeforeEach
    void setUp(){
        validPost = PostBuilder.build();
        validWriter = MemberBuilder.build();
    }
    @Test
    @DisplayName("댓글_업데이트_성공")
    void updateComment_GivenValidContent_ShouldSuccess() {
        final String UPDATE_VALID_CONTENT = "content";
        final Comment validComment = CommentBuilder.builder(validWriter, validPost);
        //when
        validComment.updateComment(UPDATE_VALID_CONTENT);

        //then
        assertThat(validComment.getContent()).isEqualTo(UPDATE_VALID_CONTENT);
    }


    @Test
    @DisplayName("댓글_업데이트_실패_null_content")
    void updateComment_GivenNullContent_ShouldFail() {
        final String UPDATE_INVALID_CONTENT = null;
        final Comment validComment = CommentBuilder.builder(validWriter, validPost);

        assertThrows(IllegalArgumentException.class, () -> {
            validComment.updateComment(UPDATE_INVALID_CONTENT);
        });
    }
}