package com.efub.community.domain.board.domain;

import com.efub.community.domain.board.domain.builder.BoardBuilder;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Member validOwner;

    @BeforeEach
    public void setUp(){
        validOwner = MemberBuilder.build();
    }

    @Test
    @DisplayName("게시판_업데이트_성공")
    void updateBoard_GivenValidContent_ShouldUpdateContent(){
        final Member validOwner = MemberBuilder.build(1L);
        final Board board = BoardBuilder.build(validOwner);
        final String UPDATE_VALID_CONTENT = "update";

        //when
        board.updateBoard(validOwner, UPDATE_VALID_CONTENT);

        //then
        assertThat(board.getDescription()).isEqualTo(UPDATE_VALID_CONTENT);

    }

    @Test
    @DisplayName("게시판_업데이트_실패_null_description")
    void updateBoard_WithNullDescription_ShouldFail() {
        final Board board = BoardBuilder.build(validOwner);
        final String UPDATE_INVALID_CONTENT = null;

        assertThrows(NullPointerException.class, () -> {
            board.updateBoard(validOwner, UPDATE_INVALID_CONTENT);
        });
    }

    @Test
    @DisplayName("게시판_업데이트_실패_invalid_owner")
    void updateBoard_WithNullOwner_ShouldFail() {
        final Long invalidOwnerId = 2l;
        final Member invalidOwner = MemberBuilder.build(invalidOwnerId);


        final Board board = BoardBuilder.build(validOwner);
        final String VALID_DESCRIPTION = "update content";

        assertThrows(IllegalArgumentException.class, () -> {
            board.updateBoard(invalidOwner, VALID_DESCRIPTION);
        });
    }



}