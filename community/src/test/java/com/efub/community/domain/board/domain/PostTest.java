package com.efub.community.domain.board.domain;

import com.efub.community.domain.board.domain.builder.BoardBuilder;
import com.efub.community.domain.board.domain.builder.PostBuilder;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    private Member validWriter;
    private Board validBoard;

    @BeforeEach
    public void setUp(){
        validWriter = MemberBuilder.build();
        validBoard = BoardBuilder.build(validWriter);
    }


    @Test
    @DisplayName("게시글_생성_성공")
    void GivenValidContent_ShouldBeCreated() {
        //given
        final String UPDATE_VALID_CONTENT = "content";

        //when
        final Post post = PostBuilder.build(validWriter, validBoard);

        //then
        assertThat(post.getContent()).isEqualTo(UPDATE_VALID_CONTENT);
    }


    @Test
    @DisplayName("게시글_업데이트_성공")
    void updatePost_GivenValidContent_ShouldSuccess() {
        final Post post = PostBuilder.build(validWriter, validBoard);
        final String UPDATE_VALID_CONTENT = "content";

        //when
        post.updatePost(UPDATE_VALID_CONTENT);

        //then
        assertThat(post.getContent()).isEqualTo(UPDATE_VALID_CONTENT);
    }


    @Test
    @DisplayName("게시글_업데이트_실패_null_content")
    void updatePost_GivenNullContent_ShouldFail() {
        final Post post = PostBuilder.build(validWriter, validBoard);
        final String UPDATE_INVALID_CONTENT = null;


        assertThrows(IllegalArgumentException.class, () -> {
            post.updatePost(UPDATE_INVALID_CONTENT);
        });
    }


}