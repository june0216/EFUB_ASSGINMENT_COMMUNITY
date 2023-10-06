package com.efub.community.domain.board.domain;

import com.efub.community.domain.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {


    @Test
    @DisplayName("게시판_업데이트_성공")
    void updateBoard_GivenValidContent_ShouldUpdateContent(){
        Member validOwner = createDefaultMember();
        Board board = createDefaultBoard(validOwner);
        final String UPDATE_VALID_CONTENT = "update";

        //when
        board.updateBoard(validOwner, UPDATE_VALID_CONTENT);

        //then
        assertThat(board.getDescription()).isEqualTo(UPDATE_VALID_CONTENT);

    }

    @Test
    @DisplayName("게시판_업데이트_실패_null_description")
    void updateBoard_WithNullDescription_ShouldFail() {
        Member validOwner = createDefaultMember();
        Board board = createDefaultBoard(validOwner);
        final String UPDATE_INVALID_CONTENT = null;


        assertThrows(IllegalArgumentException.class, () -> {
            board.updateBoard(validOwner, UPDATE_INVALID_CONTENT);
        });
    }

    @Test
    @DisplayName("게시판_업데이트_실패_invalid_owner")
    void updateBoard_WithNullOwner_ShouldFail() {
        final Long validOwnerId = 1L;
        final Long invalidOwnerId = 2l;
        Member validOwner = createDefaultMember();
        validOwner.setMemberId(validOwnerId);
        Member invalidOwner = createDefaultMember();
        invalidOwner.setMemberId(invalidOwnerId);

        Board board = createDefaultBoard(validOwner);
        final String VALID_DESCRIPTION = "update content";

        assertThrows(IllegalArgumentException.class, () -> {
            board.updateBoard(invalidOwner, VALID_DESCRIPTION);
        });
    }




    private Board createDefaultBoard(Member owner){
        final String VALID_NAME = "board_name";
        final String VALID_DESCRIPTION = "this is board";
        final Member VALID_OWNER = owner;
        return Board.builder()
                .name(VALID_NAME)
                .description(VALID_DESCRIPTION)
                .owner(VALID_OWNER)
                .build();
    }

    private Member createDefaultMember() {
        final String TEST_EMAIL = "test@gmail.com";
        final String TEST_NICKNAME = "테스트계정";
        final String TEST_ENCODED_PASSWORD = "encodedPassword";
        final String TEST_UNIVERSITY = "이화여자대학교";
        final Integer TEST_STUDENT_NO = 1989001;

        return Member.builder()
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .encodedPassword(TEST_ENCODED_PASSWORD)
                .university(TEST_UNIVERSITY)
                .studentNo(TEST_STUDENT_NO)
                .build();
    }
}